import java.util.*;

public class Main {
    static int accountBalance = 0;
    static int i = 0;
    static String[][] operations = new String[i][2];
    static String[][] allOperations = new String[i][2];
    public static void main(String[] args) {
        System.out.println("Добро пожаловать в финансовый помощник!\n");
        menu(new Scanner(System.in));
    }

    public static void menu(Scanner sc) {
        System.out.println("""
                Выберите пункт меню (введите цифру):
                1 - Добавить трату/пополнение
                2 - Удалить трату/пополнение 
                3 - Узнать текущий счет 
                4 - Вывести все траты/пополнения 
                5 - Вывести все траты по определенной категории
                0 - Выход
                """);
        int point = sc.nextInt();
        switch (point) {
            case 1 -> add();
            case 2 -> remove();
            case 3 -> balance();
            case 4 -> showAllOperations();
            case 5 -> showCategoryExpenses();
            case 0 -> exit();
            default -> {
                System.out.println("Выбранный пункт отсутствует, попробуйте снова\n");
                menu(new Scanner(System.in));
            }
        };
        sc.close();
    }

    public static void add() {
        System.out.println("Введите категорию и сумму (со знаком \"+\" для пополнения/\"-\" для траты):");
        Scanner input = new Scanner(System.in);
        String operation = input.nextLine();
        int sum = Integer.parseInt(operation.split(" ")[1]);
        if (accountBalance + sum < 0) {
            System.out.println("Остаток на счете меньше траты\n");
            menu(new Scanner(System.in));
        }
        else accountBalance += sum;
        allOperations = new String[++i][2];
        for (int j = 0; j < allOperations.length - 1; j++) {
            for (int k = 0; k < 2; k++) {
                allOperations[j][k] = operations[j][k];
            }
        }
        allOperations[i - 1] = operation.split(" ");
        operations = new String[i][2];
        for (int j = 0; j < allOperations.length; j++) {
            for (int k = 0; k < 2; k++) {
                operations[j][k] = allOperations[j][k];
            }
        }
        for (String[] allOperation : allOperations) {
            System.out.println(Arrays.toString(allOperation));
        }

        menu(new Scanner(System.in));
    }

    public static void remove() {
        System.out.println("Введите номер траты/пополнения, которую необходимо удалить:");
        Scanner input = new Scanner(System.in);
        int num = input.nextInt();
        if (num <= allOperations.length && accountBalance - Integer.parseInt(allOperations[num - 1][1]) >= 0) {
            accountBalance -= Integer.parseInt(allOperations[num - 1][1]);
            allOperations = new String[--i][2];
            for (int j = 0; j < num - 1; j++) {
                for (int k = 0; k < 2; k++) {
                    allOperations[j][k] = operations[j][k];
                }
            }
            for (int j = num - 1; j < allOperations.length; j++) {
                for (int k = 0; k < 2; k++) {
                    allOperations[j][k] = operations[j + 1][k];
                }
            }
            operations = new String[i][2];
            for (int j = 0; j < allOperations.length; j++) {
                for (int k = 0; k < 2; k++) {
                    operations[j][k] = allOperations[j][k];
                }
            }
        }
        else if (num > allOperations.length) System.out.println("Операция отсутствует\n");
        else if (accountBalance - Integer.parseInt(allOperations[num - 1][1]) < 0)
            System.out.println("Остаток на счете не может быть меньше нуля\n");
        menu(new Scanner(System.in));
    }

    public static void balance() {
        System.out.printf("Ваш текущий счет: %d\n\n", accountBalance);
        menu(new Scanner(System.in));
    }

    public static void showAllOperations() {
        System.out.println("Ваши траты/пополнения:");
        if (i == 0) System.out.println("Операции отсутствуют\n");
        for (int j = 0; j < allOperations.length; j++) {
            System.out.print(j + 1 + ". ");
            for (int k = 0; k < 2; k++) {
                System.out.print(allOperations[j][k] + " ");
            }
            System.out.println();

        }
        System.out.println();
        menu(new Scanner(System.in));
    }

    public static void showCategoryExpenses() {
        System.out.println("Введите название категории: ");
        Scanner input = new Scanner(System.in);
        String category = input.nextLine();
        boolean flag = false;
        System.out.println("Ваши траты по категории " + category + ":");
        for (int j = 0; j < allOperations.length; j++) {
            if (allOperations[j][0].equals(category)) {
                flag = true;
                System.out.println(j + ". " + allOperations[j][0] + " " + allOperations[j][1]);
            }
        }
        System.out.println();
        if (flag == false) System.out.println("Отсутствуют\n");
        menu(new Scanner(System.in));
    }

    public static void exit() {
        System.exit(0);
    }
}