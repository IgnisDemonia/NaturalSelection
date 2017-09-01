import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Doge on 29.08.2017.
 *
 * ///DO THE EVOLUTION\\\
 *
 * Основная цель этой программы - создание у пользователя общего представления об эволюции в целом и о естественном отборе,
 * как главном её инструменте, в частности.
 *
 * Вам предстоит проследить механизм обмена генов между особями, а так же осознать могущество случайности
 * в жизни каждого индивида. Вы не будете иметь представления об имени каждой особи, о внешности и т.д.,
 * потому как с точки зрения эволюции каждая особь каждого вида - это просто набор генов.Ни больше, ни меньше(та же внешность - лишь гены).
 * Вот в виде набора генов вам и будет представляться каждая особь.
 *
 * Итак.
 * Набор генов каждой особи будет иметь вид массива:
 *
 * {1, 2, 3, 4, 5, 6, 7}.
 *
 * Подробнее о каждой ячейке:
 * 1 - счётчик поколения. Это поле не позволит вам потеряться во всём питомнике, который будет разведён в вашей консоли.
 * 4, 5, 6 ,7 - четыре случайных гена (например: цвет глаз, скорость роста, степень оволосения, эффективность регенерации и т.д.).
 * Здесь следует оговориться, что генов - многие тысячи, но для демонстрации механизма обмена хватит и четырёх.
 * Так же, забегая вперёд, скажу, что начинаться программа будет с десяти первоначальных уникальных особей,
 * а это значит, что у каждого из этих четырёх генов будет изначально 10 вариаций.
 * Некоторые из них пропадут в ходе гонки за жизнь(вместе с носителями, конечно).
 * Существуют гены доминантные, и рецессивные, и их проявление подчиняется определённым законам, однако, в этой программе
 * доминирующий  ген определяется с вероятностью 50% (в жизни процент несколько иной). Сделано это для упрощения.
 * 2 - ячейка, определяющая количество негативных мутаций. Если счётчик у индивида повысился, значит - один из родителей
 * где - то  приобрёл негативную мутацию в одном из тысяч не вошедших в массив генов, что попортит жизнь особи.
 * Так же очень велика вероятность, что вредная генная мутация убъёт особь ещё в младенчестве.
 * Вероятность возникновения негативной мутации - 5%.
 * Вероятность смерти в младенчестве от негативной мутации - 85%.
 * Если мутация не летальна, то она лишь понижает шансы особи выжить в ходе естественного отбора на 5%.
 * Если таковых несколько, то эффект складывается.
 * 3 - ячейка, определяющая количество положительных мутаций.
 * Положительные мутации повышают вероятность выживания в ходе естественного отбора на 5%.
 * Если таковых несколько, то эффект складывается.
 *
 * Инструкция по применению:
 * Всё, что от вас требуется : наблюдать за консолью, и вводить любое значение в консоль (только не пустое, не NO и не N),
 * при желании увидеть следующее поколение.
 * Также не удивляйтесь, что мутации (в т.ч. с летальными исходами) будут происходить до столкновения особей с реальным миром :
 * мутации врождённые.
 *
 * Информация о виде:
 * 1.Вид моногамный. Один партнёр на всю жизнь.
 * 2.Количество мужских и женских особей у этого вида всегда равно (небольшое расхождение с жизнью, но меня понять можно).
 * 3.Количество детей у каждой пары : 3 - 6 (не очень плодовитый вид).
 *
 * Объяснение алгоритма работы кода (необязательная часть):
 * 1.Создаётся стартовое поколение(список) из 10-ти особей(== массивов).Они попадают под полноценный естественный отбор (см.ниже).
 * 2.С вероятностью 45% каждая особь до оставления потомства не доживает ввиду различных причин. Список чистится от "мёртвых".
 * 3.Выжившие особи перемешиваются случайным образом, и происходит обмен генами между соседними. Перемешивание
 * производится с целью распространения различных вариаций генов, и избежания сплошного инцеста.
 * Если количество выживших особей нечётно, то в списке массивов удаляется последний элемент (как не нашедший пару).
 * 4.Случайным образом определяется кол - во детей у каждой пары, и каждому ребёнку этой пары даются гены родителей.
 * Затем ребёнок вносится в новый список следующего поколения. Если он получил летальную мутацию, то "вычищается" из списка.
 * 5.Первоначальному списку присваивается новый, что позволяет новому списку пройти тот же круг, подобно старому.
 *
 * Если вам недостаточно 10- ти поколений для демонстрации, то вам следует присвоить переменной countOfGenerations
 * соответствующее значение.
 *
 * Код трудночитаемый. Отчасти это объясняется стремлением придать некоторую адекватную форму выведенной в консоль информации,
 * но основная причина - этот код делал я.
 * Так что если у вас будет желание измазаться в нём, то я буду рад, если вы сделаете некоторые замечания (насчёт кода, а не моих
 * познаний в биологии).
 */
public class NaturalSelection {
    public static void main(String[] args) throws IOException {
        System.out.println("Первое поколение: ");
        int[][] firstIndividuals = {{1, 0, 0, 1, 1, 1, 1},
                                    {1, 0, 0, 2, 2, 2, 2},
                                    {1, 0, 0, 3, 3, 3, 3},
                                    {1, 0, 0, 4, 4, 4, 4},
                                    {1, 0, 0, 5, 5, 5, 5},
                                    {1, 0, 0, 6, 6, 6, 6},
                                    {1, 0, 0, 7, 7, 7, 7},
                                    {1, 0, 0, 8, 8, 8, 8},
                                    {1, 0, 0, 9, 9, 9, 9},
                                    {1, 0, 0, 10, 10, 10, 10}};

        ArrayList<int[]> nextGeneration = new ArrayList<int[]>();
        for (int i = 0; i < firstIndividuals.length; i++) {
            nextGeneration.add(firstIndividuals[i]);
        }

        for (int i = 0; i < nextGeneration.size(); i++) {
            System.out.print(Arrays.toString(nextGeneration.get(i)) + " ▼ ");
            if ( i == 4) {
                System.out.println();
            }
        }
        System.out.println();
        System.out.println("Вывести следующее поколение?(Y/N)");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int countOfGenerations = 0;
        while (true) {
            countOfGenerations += 1;
            if (countOfGenerations == 20) {
                Monkeys human = new Monkeys("Humanity");
                human.doTheEvolution();
                break;
            }
            String answer = reader.readLine();
            if (!answer.isEmpty() && !answer.toLowerCase().equals("no") && !answer.toLowerCase().equals("n")) {

                possibilityOfRandomEvent(nextGeneration);
                mixIndividuals(nextGeneration);
                for (int i = 0; i < nextGeneration.size(); i++){
                    if (nextGeneration.get(i) == null) {
                        nextGeneration.remove(i);
                        i--;
                    }
                }
                choiceOfPartner(nextGeneration);

                ArrayList<int[]> listOfNextGeneration = new ArrayList<int[]>();
                for (int i = 0; i < nextGeneration.size(); i += 2) {
                    int childsOfThisPair = quantityOfNewChilds(nextGeneration.get(i), nextGeneration.get(i + 1));
                    for (int z = 0; z < childsOfThisPair; z++) {
                        int[] child = makeNextGeneration(nextGeneration.get(i), nextGeneration.get(i + 1));
                        listOfNextGeneration.add(child);
                    }
                }

                for (int i = 0; i < listOfNextGeneration.size(); i++){
                    if (listOfNextGeneration.get(i) == null) {
                        listOfNextGeneration.remove(i);
                         i--;
                    }
                }

                System.out.println("Новое поколение : ");
                for (int i = 0; i < listOfNextGeneration.size(); i++) {
                    System.out.print(Arrays.toString(listOfNextGeneration.get(i)) + " ▼ ");
                    if ((i != 0) && (i % 7 == 0)) {
                        System.out.println();
                    }
                }
                nextGeneration = listOfNextGeneration;
                System.out.println();
                System.out.println("Вывести следующее поколение?(Y/N)");
            } else {
                reader.close();
                break;
            }
        }
    }


    private static int newBadMutation() {
        return (int)(Math.random() + 0.05);
    }


    private static boolean isBadMutationReallyBad() {
        int dangerous = (int)(Math.random() + 0.85);
        if (dangerous == 0) {
            return false;
        }
        else {
            return true;
        }
    }


    private static int newGoodMutation () {
        return (int) (Math.random() + 0.05);
    }


    private static int definitionOfDominantFeature (int firstFeature, int secondFeature) {
        int x = (int) (Math.random() + 0.5);
        if (x == 0) {
            return secondFeature;
        } else {
            return firstFeature;
        }
    }


    private static void mixIndividuals(ArrayList<int[]> currentGeneration) {
        Collections.shuffle(currentGeneration);
    }


    private static int quantityOfNewChilds (int[] firstIndividual, int[] secondIndividual) {
        return (int) ((Math.random() * 4) + 3);
    }


    static int[]  makeNextGeneration (int[] firstIndividual, int[] secondIndividual) {
        int resultFeature = 0;
        int[] child = {0, secondIndividual[1] + firstIndividual[1], secondIndividual[2] + firstIndividual[2], 0, 0, 0, 0};
        for (int i = 3; i < 7; i++) {
            resultFeature = definitionOfDominantFeature(firstIndividual[i], secondIndividual[i]);
            child[i] = resultFeature;
        }

        child[0] = firstIndividual[0] + 1;

        if (newBadMutation() == 1) {
            isBadMutationReallyBad();
            if (!isBadMutationReallyBad()) {
                System.out.println("Особь " + Arrays.toString(child) + " приобретает вредную мутацию.");
                child[1] += 1;
            } else if (isBadMutationReallyBad()) {
                System.out.println("Особь " + Arrays.toString(child) + " приобретает вредную мутацию." +
                        " Вредная мутация " +Arrays.toString(child) + " критична, и приводит к смерти раньше оставления потомства.");
                return null;
            }
        }

        if (newGoodMutation() == 1) {
            System.out.println("Особь" + Arrays.toString(child) + " приобретает полезную мутацию.");
            child[2] += 1;
        }

        return child;
    }

    private static void possibilityOfRandomEvent (ArrayList<int[]> generation) {
        for (int i = 0; i < generation.size(); i++){
            double possibility = 0.45;
            for (int z = 0; z < generation.get(i)[2]; z++){
                possibility *= 0.95;
            }
            for (int x = 0; x < generation.get(i)[1]; x++){
                possibility *= 1.05;
            }
            int resultPossibility = (int)(Math.random() + possibility);
            if (resultPossibility == 1) {
                System.out.println(Arrays.toString(generation.get(i)) + shitHappens());
                generation.remove(i);
            }
        }
    }

    private static String shitHappens () {
        int event = (int)(Math.random() * 10);
        String textOfReason;
        switch (event) {
            case 0 :
                textOfReason = "Особь была съедена враждебным видом, не успев оставить потомство.";
                break;
            case 1 :
                textOfReason = "Особь съела несвежую пищу, и, отравившись, умерла, не успев оставить потомство.";
                break;
            case 2 :
                textOfReason = "Особь погибла в схватке с конкурирующей особью за право оставить потомство, не успев оставить потомство.";
                break;
            case 3 :
                textOfReason = "Особь не скопила достаточный запас жира на зиму и замёрзла, не успев оставить потомство.";
                break;
            case 4 :
                textOfReason = "Особь получила болезнь, с которой не смог справиться её иммунитет, и умерла, не успев оставить потомство.";
                break;
            case 5 :
                textOfReason = "Особь погибла от внезапно упавшего метеорита, не успев оставить потомство.";
                break;
            case 6 :
                textOfReason = "Особь умерла от разрыва сердца, не успев оставить потомство.";
                break;
            case 7 :
                textOfReason = "Особь не сумела добыть пищу, и умерла от голода, не успев оставить потомство.";
                break;
            case 8 :
                textOfReason = "Особь упала с большой высоты и разбилась, не успев оставить потомство.";
                break;
            default :
                textOfReason = "Особь оказалась очень тупой, и умерла глупой смертью, не успев оставить потомство.";
        }
        return textOfReason;
    }

    private static void choiceOfPartner (ArrayList<int[]> generation) {
        if ((generation.size()) % 2 == 1) {
            System.out.println("***Особь " + Arrays.toString(generation.get(generation.size() - 1)) + "не смогла найти партнёра, и канет в Лету.");
            generation.remove(generation.get(generation.size() - 1));
        }
    }
}

