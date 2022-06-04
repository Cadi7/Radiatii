package sample;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class Controller {
    private Stage stage;
    private Parent root;
    private Stage back;

    static Scanner cin = new Scanner(System.in);
    static int n;
    static int m;


    public static Vector<Vector<Short>> citesteFisier() {
        Vector<Vector<Short>> A = null;
        try {
            Scanner scan = new Scanner(new File("Radiatii.in"));
            n = scan.nextShort();
            m = scan.nextShort();
            A = new Vector<>(n);
            for (short i = 0; i < n; i++) {
                A.add(new Vector<>(m));
                for (short j = 0; j < m; j++) {
                    A.get(i).add(scan.nextShort());
                }
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("Fisierul nu a fost gasit");
            e.printStackTrace();
        }
        return A;
    }


    // aici avem metoda prin care afisam matricea din fisier.
    public static void afisareFisier(Vector<Vector<Short>> A) {
        System.out.println("Matricea obtinuta din fisier este: " + "\n");
        for (Vector<Short> shorts : A) {
            for (int coloana = 0; coloana < A.get(0).size(); coloana++)
                System.out.print(shorts.get(coloana) + " ");
            System.out.println();
        }
    }

    static void inscriereInFisier(Vector<Vector<Short>> A) {
        short mod;
        System.out.println("Ce doriti sa schimbati ?");
        System.out.println("0 - o coloana");
        System.out.println("1 - un rand");
        System.out.print(">>> ");
        mod = cin.nextShort();
        switch (mod) {
            case 0 -> {
                System.out.println("Numarul coloanei: ");
                short col = cin.nextShort();
                if (col >= m) {
                    System.out.println("Nu puteti modifica o coloana inexistenta.");
                    System.exit(0);
                }
                System.out.println("Introduceti elementele coloanei, ele sunt cel mult" + A.size() + " la numar.");
                for (int i = 0; i < m; i++)
                    A.get(i).set(col, cin.nextShort());

            }
            case 1 -> {
                System.out.println("Numarul randului: ");
                short r = cin.nextShort();
                if (r >= n) {
                    System.out.println("Nu puteti modifica un rand inexistent..");
                    System.exit(0);
                }
                System.out.println("Introduceti elementele randului, ele sunt " + A.size() + " la numar.");
                for (int i = 0; i < n; i++)
                    A.get(r).set(i, cin.nextShort());
            }
            default -> System.out.println("Introduceti un mod valid");
        }

        try {
            FileWriter fw = new FileWriter("Radiatii.in");
            fw.write(n + " " + m + " " + "\n");
            for (short i = 0; i < n; i++) {
                for (short j = 0; j < m; j++) {
                    fw.write(A.get(i).get(j) + " ");
                }
                fw.write("\n");
            }
            fw.close();
            System.out.println("Elementele matricii de acum sunt: ");
            afisareFisier(A);
        } catch (IOException e) {
            System.out.println("Eroare");
        }
    }
    static void StergereDinFisier(Vector<Vector<Short>> A) throws IOException {
        short mod;

        System.out.println("Ce doriti sa stergeti ?");
        System.out.println("0 - o coloana");
        System.out.println("1 - un rand");
        System.out.print(">>> ");
        mod = cin.nextShort();
        switch (mod) {
            case 0 -> {
                System.out.println("Numarul coloanei: ");

                short col = cin.nextShort();
                if (col >= m) {
                    System.out.println("Nu puteti sterge o coloana inexistenta.");
                    System.exit(0);
                }
                for (Vector<Short> vec : A)
                    vec.remove(col);
                m--;
            }

            case 1 -> {
                System.out.println("Numarul randului: ");
                short rand = cin.nextShort();
                if (rand >= m) {
                    System.out.println("Nu puteti sterge un rand inexistent.");
                    System.exit(0);
                }
                A.remove(rand);
                //  System.out.println(A);
                n--;
            }
            default -> System.out.println("Introduceti un mod valid");
        }
    }

    // cu ajutorul functiei respective, actualizam datele din fisier.
    static void update(Vector<Vector<Short>> A) {
        try {
            FileWriter fw = new FileWriter("Radiatii.in");
            fw.write(n + " " + m + " " + "\n");
            for (Vector<Short> shorts : A) {
                for (int coloana = 0; coloana < A.get(0).size(); coloana++) {
                    fw.write(shorts.get(coloana) + " ");
                }
                fw.write("\n");
            }
            fw.close();
            System.out.println("Elementele matricii de acum sunt: ");
            afisareFisier(A);
        } catch (IOException e) {
            System.out.println("Eroare");
        }
    }

    // cu ajutorul metodei respective, cautam linia cu cea mai mica doza de radiatie.
    static void indici(Vector<Vector<Short>> A) {
        ArrayList<Integer> b = new ArrayList<>();
        int minsum = 0;

        for (Vector<Short> shorts : A) {
            for (int coloana = 0; coloana < A.get(0).size(); coloana++) {
                minsum = shorts.get(coloana) + minsum;
            }
            b.add(minsum);
            minsum = 0;
        }
        // System.out.println(b); afisez suma tuturor liniilor pentru a verifica daca s-au calculator corect.

        Comparable<Integer> min = Collections.min(b);
        System.out.println("Cea mai mica doza de radiatie o are linia  " + b.indexOf(min) + " si are valoarea " + min);
    }

    // mai jos avem primul rand din matrice sortat ascendent prin metoda selectiei
    public static void sortare(Vector<Vector<Short>> A) {

        Vector<Short> B = new Vector<>();
        for (int j = 0; j < A.get(0).size(); j++) {
            // System.out.print(A.get(1).get(j) + " ");
            short w = A.get(1).get(j);
            B.add(w);
        }
        // Ca sa fie mai usor, adaug acel rand in alt vector.
        System.out.println("Randul 1 inainte de sortare este: " + B);
        for (int i = 0; i < B.size(); i++) {
            for (int ii = 0; ii < B.size(); ii++) {
                if (B.get(i) > B.get(ii)) {
                    Collections.swap(B, i, ii);
                }
            }
        }
        System.out.println("Randul 1 dupa sortare metoda selectiei in vectorul B este " + B);
    }


    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    // functia de mai jos va calcula doza medie de radiatie de pe intreg teritoriul
    static void dozamedie(Vector<Vector<Short>> A) {
        int sum = 0;
        for (Vector<Short> shorts : A) {
            for (int coloana = 0; coloana < A.get(0).size(); coloana++) {
                sum = shorts.get(coloana) + sum;
            }
        }
        double media = sum / (A.size() * A.get(0).size());
        System.out.println("Media dozelor de pe intregul teritoriu este: " + media);
    }

    // cu ajutorul metodei de mai jos determinam randurile pare din matrice.
    public static void parlin(Vector<Vector<Short>> A) throws IOException {
        System.out.println("Mai jos vom verifica fiecare rand din matrice, puteti urmari procesul.");
        System.out.println();
        FileWriter gw = new FileWriter("ParLin.txt");
        int mw = 0;
        Vector<Short> B = new Vector<>();
        for (Vector<Short> shorts : A) {
            for (int coloana = 0; coloana < A.get(0).size(); coloana++) {
                B.add(shorts.get(coloana));
            }
            System.out.println(B);
            for (Short o : B) {
                Short ww = 2;
                if (o % ww == 0)
                    mw++;
            }
            // B.clear();
            if (mw == B.size()) {
                System.out.println("A fost gasit un rand complet par si trimis in fisierul ParLin.");
                // System.out.println("Avem un rand complet par");
                gw.write(B + "\n");
            } else {
                System.out.println("Randul verificat nu este par.");
            }
            B.clear();
            mw = 0;
        }
        gw.close();
        System.out.println();
        // Ca sa fie totul mai simplu, iau fiecare rand si il adaug in vectorul B, mai apoi il verific daca este complet par.
        // Daca este par, se trimite in fisier.
        File file = new File("ParLin.txt");
        if (file.length() == 0)
            System.out.println("Din pacate nu am avut linii pare iar fisierul este gol.");
        else
            System.out.println("Fisierul a primit date cu succes, puteti verifica!");
    }

    // aici avem functia prin care determinam numarul de patratele trase de diagonala, dupa un algoritm special.
    static void diagonala() {
        int dc = 0;
        for (int i = 2; i <= n; i++) {
            if (n % i == 0 && m % i == 0) {
                dc = i;
                break;
            }
        }
        if (dc == 0) {
            if (n > m) dc = m - 1;
            else dc = n - 1;
        }

        int count = 0;
        for (int i = 1; i <= n; i++) {
            for (int ii = 1; ii <= m; ii++) {
                int delta = i * m - n * ii;
                if (delta >= 0 || Math.abs(delta) != (dc * dc)) {
                    if (Math.abs(delta) <= (dc * dc)) count++;
                }
            }
        }

        System.out.println("Vom avea trasate " + count + " patratele de catre diagonala.");
    }

    // aici avem o metoda prin care luam datele din fisier si le introducem intr-un array pentru metoda cu cercetator.
    static int[][] getList() {
        int[][] a = new int[100][100];
        try {
            Scanner cin = new Scanner(new File("Radiatii.in"));

            n = cin.nextInt();
            m = cin.nextInt();

            for (int i = 1; i <= n; i++) {
                for (int ii = 1; ii <= m; ii++) {
                    a[i][ii] = cin.nextInt();
                }
            }
        } catch (Exception ignored) {
        }
        return a;
    }

    // aici avem metoda prin care cercetatorul trebuie sa parcurga din punctul 1 1 in n m si sa primeasca doza minima de radiatie.
    static void certataorul() throws IOException {

        if (n <= 3) {
            System.out.println("Pentru a primi un raspuns optim si cu o precizie mai mare, incercati sa adaugati cel putin 4 randuri!");
            System.exit(0);
        }
        if (m >= 30) {
            System.out.println("Nu putem avea mai mult de 30 coloane!");
            System.exit(0);
        }
        if (n * m > 100) {
            System.out.println("Nu putem avea mai mult de 100 elemente!");
            System.exit(0);
        }
        FileWriter ww = new FileWriter("Radiatii.out");
        int[][] a = getList();
// aici alocam vectorii care ne vor ajuta mai tarziu
        int[][] b = new int[n + 1][m + 1];
        int[][] c = new int[n + 1][m + 1];

        int[] di = {-1, 0, 1, 0};
        int[] dii = {0, -1, 0, 1};


        System.out.println("Drumul schematic pe care il va parcurge cercetatorul este:  ");
        System.out.println();
        // prin instructiunile de mai jos cautam cele mai mici numere care in suma la fel dau un numar mic.
        for (int i = 1; i <= n; i++) {
            for (int ii = 1; ii <= m; ii++) {
                int min;
                if (i == 1) min = b[i][ii - 1];
                else if (ii == 1) min = b[i - 1][ii];
                else min = Math.min(b[i - 1][ii], b[i][ii - 1]);

                b[i][ii] = a[i][ii] + min;
            }
        }

        int x = n;
        int y = m;

        ArrayList<Integer> pi = new ArrayList<>();
        ArrayList<Integer> pii = new ArrayList<>();

        pi.add(x);
        pii.add(y);

        // aici deja organizam vectorul sub forma de numere ca mai tarziu sa putem trasa.
        while (x != 1 || y != 1) {
            int xmin = -1;
            int ymin = -1;
            for (int d = 0; d <= 3; d++) {
                if (x == n && d == 2) d++;
                if (x == 1 && d == 0) d++;
                if (y == m && d == 3) break;
                if (y == 1 && d == 1) d++;
                int ni = x + di[d];
                int nii = y + dii[d];
                if (xmin == -1 && c[ni][nii] == 0) {
                    xmin = ni;
                    ymin = nii;
                    c[ni][nii] = 1;
                } else if (ni < c.length && nii < b.length && c[ni][nii] == 0 && b[ni][nii] < b[xmin][ymin]) {
                    xmin = ni;
                    ymin = nii;
                    c[ni][nii] = 1;
                }
            }
            x = xmin;
            y = ymin;
            pi.add(x);
            pii.add(y);
        }

        int sum = 0;
        for (int o = 0; o < pi.size(); o++) {
            sum += a[pi.get(o)][pii.get(o)];
        }

        // cu ajutorul instructiunilor de mai jos, trasam schematic drumul parcurs de cercetator.
        for (int i = 1; i <= n; i++) {
            for (int ii = 1; ii <= m; ii++) {
                int ko = 0;
                for (int o = 0; o < pi.size(); o++) {
                    if (i == pi.get(o) && ii == pii.get(o)) {
                        ko = 1;
                        break;
                    }
                }
                if (ko == 1) System.out.print("* ");
                else
                    System.out.print("O ");
            }
            System.out.println();
        }
        System.out.println();

        System.out.println("Radiatia minima pe care cercetatorul trebuie sa o primeasca este:  " + sum);
        ww.write(String.valueOf(sum));

        File file = new File("Radiatii.out");
        if (file.length() == -1)
            System.out.println("Din pacate a avut loc o eroare iar fisierul este gol.");
        else
            System.out.println("Fisierul a primit date cu succes, puteti verifica!");
        ww.close();
        System.out.println();
    }

    // metoda respectiva va afisa toate perechile de 2 numere care adunate dau numarul dat de la tastatura.
    // Perechile vor fi luate mai intai pe randuri, apoi pe coloane.
    static void adaugator(Vector<Vector<Short>> A) {
        String rs;
        System.out.println("Introdu suma carora vrei sa-i gasesti perechile.");
        Short numar = cin.nextShort();
        System.out.println("Perechile de 2 numere alaturate adunate care in suma dau " + numar + " sunt: ");
        System.out.println();
        System.out.println("--------- Pe randuri ---------");
        int nr = 0;
        for (Vector<Short> shorts : A) {
            for (short j = 0; j < A.get(0).size() - 1; j++) {

                if ((shorts.get(j) + shorts.get(j + 1) == numar)) {
                    System.out.println(shorts.get(j) + " si " + shorts.get(j + 1) + " adunate vor da " + numar);
                    nr++; }
            }
        }
        if(nr==0) System.out.println("Nu avem perechi de numere alaturate egale cu " + numar);
        System.out.println();

        System.out.println("--------- Pe coloane ---------");
        nr = 0;
        for (short i = 0; i < A.get(0).size(); i++) {
            for (short j = 0; j < A.size() - 1; j++) {
                if (A.get(j).get(i) + A.get(j + 1).get(i) == numar) {
                    System.out.println(A.get(j).get(i) + " si " + A.get(j + 1).get(i) + " adunate vor da " + numar);
                    nr++; }
            }
        }
        if(nr==0) System.out.println("Nu avem perechi de numere alaturate egale cu " + numar);

    }

    static void meniu(Vector<Vector<Short>> A) {
        System.out.println();
        System.out.println(ConsoleColors.WHITE + "    |============================================================================================|");
        System.out.println(ConsoleColors.WHITE + "    * " + ConsoleColors.PURPLE + "                                         Meniu                                            " + ConsoleColors.WHITE + " *" );
        System.out.println(ConsoleColors.WHITE + "    |--------------------------------------------------------------------------------------------|");
        System.out.println(ConsoleColors.GREEN + "--> " + ConsoleColors.CYAN + "                Introduceti cifra(0-9) respectiv după condițiile de mai jos                   " + ConsoleColors.GREEN+ "<--");
        System.out.println(ConsoleColors.GREEN + "=====================================================================================================");
        System.out.println(ConsoleColors.GREEN + "* " + ConsoleColors.RED + "1. " + ConsoleColors.BLUE + "Afisarea datelor din fisier                                                                  " + ConsoleColors.GREEN + "  *");
        System.out.println(ConsoleColors.GREEN + "* " + ConsoleColors.RED + "2. " + ConsoleColors.YELLOW + "Actualizarea unei inregistrari existente in fisier.                                          " + ConsoleColors.GREEN + "  *");
        System.out.println(ConsoleColors.GREEN + "* " + ConsoleColors.RED + "3. " + ConsoleColors.YELLOW + "Stergerea unei inregistrari din fisier.                                                      " + ConsoleColors.GREEN + "  *");
        System.out.println(ConsoleColors.GREEN + "* " + ConsoleColors.RED + "4. " + ConsoleColors.YELLOW + "Determina indicii liniilor ce contin zone cu doza minima de poluare                          " + ConsoleColors.GREEN + "  *");
        System.out.println(ConsoleColors.GREEN + "* " + ConsoleColors.RED + "5. " + ConsoleColors.YELLOW + "Inscrie in tabloul B, in ordine descendenta valorile tabloului A prin metoda selectiei.      " + ConsoleColors.GREEN + "  *");
        System.out.println(ConsoleColors.GREEN + "* " + ConsoleColors.RED + "6. " + ConsoleColors.YELLOW + "Afla media dozei de pe intregul teritoriu.                                                   " + ConsoleColors.GREEN + "  *");
        System.out.println(ConsoleColors.GREEN + "* " + ConsoleColors.RED + "7. " + ConsoleColors.YELLOW + "Cream fisierul ParLin.txt unde adaugam liniile din Radiatii.in care contin doar numere pare. " + ConsoleColors.GREEN + "  *");
        System.out.println(ConsoleColors.GREEN + "* " + ConsoleColors.RED + "8. " + ConsoleColors.YELLOW + "Determinam patratelele trasate de diagonala matricii.                                        " + ConsoleColors.GREEN + "  *");
        System.out.println(ConsoleColors.GREEN + "* " + ConsoleColors.RED + "9. " + ConsoleColors.YELLOW + "Afiseaza raspunsul problemei cercetatorului!                                                 " + ConsoleColors.GREEN + "  *");
        System.out.println(ConsoleColors.GREEN + "* " + ConsoleColors.RED + "a. " + ConsoleColors.BLUE + "Afisarea perechilor de numere care dau in suma numarul dorit!                                " + ConsoleColors.GREEN + "  *");
        System.out.println(ConsoleColors.GREEN + "* " + ConsoleColors.RED + "0. " + ConsoleColors.BLUE+ "Exit.                                                                                        " + ConsoleColors.GREEN + "  *");
        System.out.println(ConsoleColors.GREEN + "=====================================================================================================");
        System.out.println(ConsoleColors.CYAN);
        //declararea unei obiectului de tip Scanner
        int stop = 0;    // o variabila care face posibila oprirea executiei consolei
        do {
            try {
                char a = cin.next().charAt(0);    //se citeste un caracter de la tastatura
                System.out.println();
                switch (a) {
                    case '0' -> System.exit(0);
                    case '1' -> afisareFisier(A);
                    case '2' -> inscriereInFisier(A);
                    case '3' -> StergereDinFisier(A);
                    case '4' -> indici(A);
                    case '5' -> sortare(A);
                    case '6' -> dozamedie(A);
                    case '7' -> parlin(A);
                    case '8' -> diagonala();
                    case '9' -> certataorul();
                    case 'a' -> adaugator(A);
                    default -> System.out.println("Introduceti un punct din meniu!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Introduceti datele corect!");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println();
            System.out.println("Poti introduce in continuare un numar de la 1 la 9. ( si a )");
        } while (stop != 1);

    }
public void back(Stage stage) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    Scene scene = new Scene(root);
    scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
    scene.setFill(Color.web("#81c483"));
    stage.setScene(scene);
    stage.setTitle("Varianta 16");
    stage.show();
}
    public void pressButton(ActionEvent actionEvent) throws Exception {
        Vector<Vector<Short>> A;
        A = citesteFisier();
       // root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sarcina1.fxml")));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        GridPane root2 = new GridPane();

        for(int y= 0; y < n; y++) {
            for (int x = 0; x < m; x++) {
                Short rand1 = A.get(y).get(x);
                TextField tf = new TextField();
                tf.setPrefHeight(50);
                tf.setPrefWidth(50);
                tf.setAlignment(Pos.CENTER);
                tf.setEditable(false);
                tf.setText("(" + rand1 + ")");

                // Iterate the Index using the loops
                root2.setRowIndex(tf,y);
                root2.setColumnIndex(tf,x);
                root2.getChildren().add(tf);
            }
        }
        root2.setAlignment(Pos.CENTER);
        VBox group2 = new VBox();
        Label text = new Label("Afisarea matricii");
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        text.setAlignment(Pos.BOTTOM_CENTER);
        group2.getChildren().add(text);
        group2.setAlignment(Pos.TOP_CENTER);
        group2.getChildren().add(root2);
        group2.setAlignment(Pos.CENTER);
        Button button = new Button("Back to menu");
        button.setFont(Font.font("Arial", FontWeight.MEDIUM, FontPosture.REGULAR, 14));

        button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    back(stage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        group2.getChildren().add(button);


        Scene scene = new Scene(group2, 400, 300);
        scene.getStylesheets().addAll(this.getClass().getResource("style2.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Sarcina 1");
        stage.show();

    }


    public void pressButton2(ActionEvent actionEvent) throws IOException {
        Vector<Vector<Short>> A;
        A = citesteFisier();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        VBox group = new VBox();

        TextField text = new TextField("Vrei sa actualizezi un rand sau o coloana?!");


        // Label text = new Label();
        group.getChildren().add(text);
        group.setAlignment(Pos.CENTER);

        Button button = new Button("Click");
        button.setFont(Font.font("Arial", FontWeight.MEDIUM, FontPosture.REGULAR, 11));

        group.getChildren().add(button);
        button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {

                switch (text.getText()) {
                    case "coloana" -> {

                        TextField text2 = new TextField("Introdu numarul coloanei");

                        group.getChildren().add(text2);
                        group.setAlignment(Pos.CENTER);

                        Button button2 = new Button("Click");
                        button2.setFont(Font.font("Arial", FontWeight.MEDIUM, FontPosture.REGULAR, 11));
                        group.getChildren().add(button2);

                        button2.setOnAction(new EventHandler<ActionEvent>() {

                            public void handle(ActionEvent event) {
                                short col = Short.parseShort(text2.getText());
                                if (col > m) {
                                    System.out.println("Nu puteti actualiza o coloana inexistenta.");
                                    System.exit(0);
                                }
                                TextField numere = new TextField("Introdu elementele coloanei");
                                group.getChildren().add(numere);
                                group.setAlignment(Pos.CENTER);

                                Button buttonn = new Button("Click");
                                buttonn.setFont(Font.font("Arial", FontWeight.MEDIUM, FontPosture.REGULAR, 11));
                                group.getChildren().add(buttonn);

                                buttonn.setOnAction(new EventHandler<ActionEvent>() {

                                    public void handle(ActionEvent event) {
                                        String[] arrO = numere.getText().split(" ", 20);
                                for (short i = 0; i < m; i++) {
                                    A.get(i).set(col, Short.parseShort(arrO[i])); }
                                        update(A);

                                        Label lab = new Label("Coloana a fost actualizata!");
                                        group.getChildren().add(lab);
                                        buttonn.setVisible(false);
                                        numere.setVisible(false);
                                    }

                                });

                                button.setVisible(false);
                                button2.setVisible(false);
                                text.setVisible(false);
                                text2.setVisible(false);

                            }

                        });

                    }


                    case "rand" -> {
                        TextField textt2 = new TextField("Introdu numarul randului");

                        group.getChildren().add(textt2);
                        group.setAlignment(Pos.CENTER);

                        Button buttonn2 = new Button("Click");
                        buttonn2.setFont(Font.font("Arial", FontWeight.MEDIUM, FontPosture.REGULAR, 11));
                        group.getChildren().add(buttonn2);

                        buttonn2.setOnAction(new EventHandler<ActionEvent>() {

                            public void handle(ActionEvent event) {
                                short rand = Short.parseShort(textt2.getText());
                                if (rand > n) {
                                    System.out.println("Nu puteti actualiza un rand inexistent.");
                                    System.exit(0);
                                }
                                TextField numere2 = new TextField("Introdu elementele randului");
                                group.getChildren().add(numere2);
                                group.setAlignment(Pos.CENTER);

                                Button buttonnn = new Button("Click");
                                buttonnn.setFont(Font.font("Arial", FontWeight.MEDIUM, FontPosture.REGULAR, 11));
                                group.getChildren().add(buttonnn);

                                buttonnn.setOnAction(new EventHandler<ActionEvent>() {

                                    public void handle(ActionEvent event) {
                                        String[] arrO = numere2.getText().split(" ", 20);
                                        for (short i = 0; i < n; i++) {
                                            A.get(rand).set(i, Short.parseShort(arrO[i]));}
                                        update(A);

                                        Label lab = new Label("Randul a fost actualizat!");
                                        group.getChildren().add(lab);
                                        buttonnn.setVisible(false);
                                        numere2.setVisible(false);

                                    }

                                });

                                button.setVisible(false);
                                buttonn2.setVisible(false);
                                text.setVisible(false);
                                textt2.setVisible(false);

                            }

                        });

                    }
                    default -> System.out.println("Introduceti un mod valid");
                }
                Button button4 = new Button("Back to menu");
                button4.setFont(Font.font("Helvica", FontWeight.EXTRA_LIGHT, FontPosture.ITALIC, 14));
                button4.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            back(stage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                group.getChildren().add(button4);

                Label texw = new Label(" \n");
                group.getChildren().add(texw);
                button.setVisible(false);
                text.setVisible(false);


            }


        }
        );

        Label texw = new Label(" \n");
        group.getChildren().add(texw);

        Scene scene = new Scene(group, 400, 350);
        scene.getStylesheets().addAll(this.getClass().getResource("style2.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Sarcina 2");
        stage.show();


    }

    public void pressButton3(ActionEvent actionEvent) throws IOException {
        Vector<Vector<Short>> A;
        A = citesteFisier();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        VBox group = new VBox();

        TextField text = new TextField("Vrei sa stergi un rand sau o coloana?!");

        group.getChildren().add(text);
        group.setAlignment(Pos.CENTER);

        Button button = new Button("Click");
        button.setFont(Font.font("Arial", FontWeight.MEDIUM, FontPosture.REGULAR, 11));
        group.getChildren().add(button);

        button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {

                    switch (text.getText()) {

                        case "coloana" -> {
                            button.setVisible(false);
                            text.setVisible(false);

                            TextField text2 = new TextField("Introdu numarul coloanei");

                            group.getChildren().add(text2);
                            group.setAlignment(Pos.CENTER);

                            Button button2 = new Button("Click");
                            button2.setFont(Font.font("Arial", FontWeight.MEDIUM, FontPosture.REGULAR, 11));
                            group.getChildren().add(button2);

                            button2.setOnAction(new EventHandler<ActionEvent>() {

                                public void handle(ActionEvent event) {
                                    short col = Short.parseShort(text2.getText());
                                    System.out.println(col);
                                    if (col > m) {
                                        //System.out.println(col);
                                        System.out.println("Nu puteti sterge o coloana inexistenta.");
                                        System.exit(0);
                                    }
                                    for (Vector<Short> vec : A)
                                        vec.remove(col);
                                    m--;
                                    update(A);
                                    Label lab = new Label("Coloana a fost stearsa!");
                                    group.getChildren().add(lab);
                                    button2.setVisible(false);
                                    text2.setVisible(false);

                                }

                            });

                        }


                        case "rand" -> {
                            button.setVisible(false);
                            text.setVisible(false);
                            TextField text3 = new TextField("Introdu numarul randului!");

                            group.getChildren().add(text3);
                            group.setAlignment(Pos.CENTER);

                            Button button3 = new Button("Click");
                            button3.setFont(Font.font("Arial", FontWeight.MEDIUM, FontPosture.REGULAR, 11));
                            group.getChildren().add(button3);

                            button3.setOnAction(new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent event) {

                            Integer rand = Integer.parseInt(text3.getText());
                            if (rand >= m) {
                                System.out.println("Nu puteti sterge un rand inexistent.");
                                System.exit(0);
                            }
                                                        A.remove(rand);
                                                        //  System.out.println(A);
                                                        n--;
                                                        update(A);
                                                        button3.setVisible(false);
                                                        text3.setVisible(false);
                                                        Label lab2 = new Label("Randul a fost sters!");
                                                        group.getChildren().add(lab2);
                                                    } });

                        }
                        default -> System.out.println("Introduceti un mod valid");
                    }

                Button button4 = new Button("Back to menu");
                button4.setFont(Font.font("Helvica", FontWeight.EXTRA_LIGHT, FontPosture.ITALIC, 14));

                button4.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            back(stage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                group.getChildren().add(button4);
                Label texw = new Label(" \n");
                group.getChildren().add(texw);

            }

        }
        );

        Label texw = new Label(" \n");
        group.getChildren().add(texw);


        Scene scene = new Scene(group, 400, 350);
        scene.getStylesheets().addAll(this.getClass().getResource("style2.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Sarcina 3");
        stage.show();
    }

    public void pressButton4(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Vector<Vector<Short>> A;
        VBox group = new VBox();
        A = citesteFisier();

        ArrayList<Integer> b = new ArrayList<>();
        int minsum = 0;

        for (Vector<Short> shorts : A) {
            for (int coloana = 0; coloana < A.get(0).size(); coloana++) {
                minsum = shorts.get(coloana) + minsum;
            }
            b.add(minsum);
            minsum = 0;
        }
        // System.out.println(b); afisez suma tuturor liniilor pentru a verifica daca s-au calculator corect.

        Comparable<Integer> min = Collections.min(b);
        Label text = new Label("Cea mai mica doza de radiatie o are linia  " + b.indexOf(min) + " si are valoarea " + min);
        group.getChildren().add(text);
        group.setAlignment(Pos.CENTER);

        Button button = new Button("Back to menu");
        button.setFont(Font.font("Arial", FontWeight.MEDIUM, FontPosture.REGULAR, 14));

        button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    back(stage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        group.getChildren().add(button);

        Scene scene = new Scene(group, 400, 100);
        scene.getStylesheets().addAll(this.getClass().getResource("style2.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Sarcina 4");
        stage.show();
    }

    public void pressButton5(ActionEvent actionEvent) throws IOException {
        Vector<Vector<Short>> A;
        A = citesteFisier();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        VBox group = new VBox();

        Vector<Short> B = new Vector<>();
        for (int j = 0; j < A.get(0).size(); j++) {
            // System.out.print(A.get(1).get(j) + " ");
            short w = A.get(1).get(j);
            B.add(w);
        }
        // Ca sa fie mai usor, adaug acel rand in alt vector.
        Label text = new Label("Randul 1 inainte de sortare este: " + B);

        for (int i = 0; i < B.size(); i++) {
            for (int ii = 0; ii < B.size(); ii++) {
                if (B.get(i) > B.get(ii)) {
                    Collections.swap(B, i, ii);
                }
            }
        }
        Label text2 = new Label("Randul 1 dupa sortare metoda selectiei in vectorul B este " + B);

        Button button = new Button("Back to menu");
        button.setFont(Font.font("Arial", FontWeight.MEDIUM, FontPosture.REGULAR, 14));

        button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    back(stage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        group.getChildren().add(text);
        group.getChildren().add(text2);
        group.setAlignment(Pos.CENTER);
        group.getChildren().add(button);

        Scene scene = new Scene(group, 400, 100);
        scene.getStylesheets().addAll(this.getClass().getResource("style2.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Sarcina 5");
        stage.show();
    }

    public void pressButton6(ActionEvent actionEvent) throws IOException {
        Vector<Vector<Short>> A;
        A = citesteFisier();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        VBox group = new VBox();

        int sum = 0;
        for (Vector<Short> shorts : A) {
            for (int coloana = 0; coloana < A.get(0).size(); coloana++) {
                sum = shorts.get(coloana) + sum;
            }
        }
        double media = sum / (A.size() * A.get(0).size());

        Label text = new Label("Media dozelor de pe intregul teritoriu este: " + media);
        group.getChildren().add(text);
        group.setAlignment(Pos.CENTER);

        Button button = new Button("Back to menu");
        button.setFont(Font.font("Arial", FontWeight.MEDIUM, FontPosture.REGULAR, 14));

        button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    back(stage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        group.getChildren().add(button);
        Scene scene = new Scene(group, 400, 100);
        scene.getStylesheets().addAll(this.getClass().getResource("style2.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Sarcina 6");
        stage.show();
    }

    public void pressButton7(ActionEvent actionEvent) throws IOException {
        Vector<Vector<Short>> A;
        A = citesteFisier();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        VBox group = new VBox();

        FileWriter gw = new FileWriter("ParLin.txt");
        int mw = 0;
        Vector<Short> B = new Vector<>();
        for (Vector<Short> shorts : A) {
            for (int coloana = 0; coloana < A.get(0).size(); coloana++) {
                B.add(shorts.get(coloana));
            }
            for (Short o : B) {
                Short ww = 2;
                if (o % ww == 0)
                    mw++;
            }
            // B.clear();
            if (mw == B.size()) {
                gw.write(B + "\n");
                Label text = new Label("Tocmai am determinat un rand par, puteti sa-l vedeti mai jos.");
                Label text2 = new Label("Rand par: " + B);
                group.getChildren().add(text);
                group.getChildren().add(text2);
            }
            B.clear();
            mw = 0;
        }
        gw.close();

        File file = new File("ParLin.txt");
        if (file.length() == 0) {
            Label text3 = new Label("Din pacate nu am avut linii pare iar fisierul este gol.");
            group.getChildren().add(text3); }
        else {

            Label text4 = new Label("Fisierul a primit date cu succes, puteti verifica!");
            group.getChildren().add(text4);
        }

        group.setAlignment(Pos.CENTER);

        Button button = new Button("Back to menu");
        button.setFont(Font.font("Arial", FontWeight.MEDIUM, FontPosture.REGULAR, 14));

        button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    back(stage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        group.getChildren().add(button);
        Scene scene = new Scene(group, 400, 150);
        scene.getStylesheets().addAll(this.getClass().getResource("style2.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Sarcina 7");
        stage.show();
    }

    public void pressButton8(ActionEvent actionEvent) throws IOException {
        Vector<Vector<Short>> A;
        A = citesteFisier();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        VBox group = new VBox();


        int dc = 0;
        for (int i = 2; i <= n; i++) {
            if (n % i == 0 && m % i == 0) {
                dc = i;
                break;
            }
        }
        if (dc == 0) {
            if (n > m) dc = m - 1;
            else dc = n - 1;
        }

        int count = 0;
        for (int i = 1; i <= n; i++) {
            for (int ii = 1; ii <= m; ii++) {
                int delta = i * m - n * ii;
                if (delta >= 0 || Math.abs(delta) != (dc * dc)) {
                    if (Math.abs(delta) <= (dc * dc)) count++;
                }
            }
        }

        Label text = new Label("Vom avea trasate " + count + " patratele de catre diagonala.");
        group.getChildren().add(text);
        group.setAlignment(Pos.CENTER);


        Button button = new Button("Back to menu");
        button.setFont(Font.font("Arial", FontWeight.MEDIUM, FontPosture.REGULAR, 14));

        button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    back(stage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        group.getChildren().add(button);
        Scene scene = new Scene(group, 400, 100);
        scene.getStylesheets().addAll(this.getClass().getResource("style2.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Sarcina 8");
        stage.show();
    }

    public void pressButton9(ActionEvent actionEvent) throws IOException {
        int[][] a = getList();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        VBox group = new VBox();

        if (n <= 3) {
            Label exit = new Label("Pentru a primi un raspuns optim si cu o precizie mai mare, incercati sa adaugati cel putin 4 randuri!");
           group.getChildren().add(exit);
            System.exit(0);
        }
        if (m >= 30) {
            Label exit = new Label("Nu putem avea mai mult de 30 coloane!");
           group.getChildren().add(exit);
            System.exit(0);
        }
        if (n * m > 100) {
            Label exit = new Label("Nu putem avea mai mult de 100 elemente!");
           group.getChildren().add(exit);
            System.exit(0);
        }
        FileWriter ww = new FileWriter("Radiatii.out");
        int[][] b = new int[n + 1][m + 1];
        int[][] c = new int[n + 1][m + 1];

        int[] di = {-1, 0, 1, 0};
        int[] dii = {0, -1, 0, 1};


       Label drum = new Label("Drumul schematic pe care il va parcurge cercetatorul este:  ");
       // group.getChildren().add(drum);
        Label sout = new Label(" ");
        group.getChildren().add(sout);

        for (int i = 1; i <= n; i++) {
            for (int ii = 1; ii <= m; ii++) {
                int min;
                if (i == 1) min = b[i][ii - 1];
                else if (ii == 1) min = b[i - 1][ii];
                else min = Math.min(b[i - 1][ii], b[i][ii - 1]);

                b[i][ii] = a[i][ii] + min;
            }
        }

        int x = n;
        int y = m;

        ArrayList<Integer> pi = new ArrayList<>();
        ArrayList<Integer> pii = new ArrayList<>();

        pi.add(x);
        pii.add(y);

        while (x != 1 || y != 1) {
            int xmin = -1;
            int ymin = -1;
            for (int d = 0; d <= 3; d++) {
                if (x == n && d == 2) d++;
                if (x == 1 && d == 0) d++;
                if (y == m && d == 3) break;
                if (y == 1 && d == 1) d++;
                int ni = x + di[d];
                int nii = y + dii[d];
                if (xmin == -1 && c[ni][nii] == 0) {
                    xmin = ni;
                    ymin = nii;
                    c[ni][nii] = 1;
                } else if (ni < c.length && nii < b.length && c[ni][nii] == 0 && b[ni][nii] < b[xmin][ymin]) {
                    xmin = ni;
                    ymin = nii;
                    c[ni][nii] = 1;
                }
            }
            x = xmin;
            y = ymin;
            pi.add(x);
            pii.add(y);
        }

        int sum = 0;
        for (int o = 0; o < pi.size(); o++) {
            sum += a[pi.get(o)][pii.get(o)];
        }

        for (int i = 1; i <= n; i++) {
            for (int ii = 1; ii <= m; ii++) {
                int ko = 0;
                for (int o = 0; o < pi.size(); o++) {
                    if (i == pi.get(o) && ii == pii.get(o)) {
                        ko = 1;
                        break;
                    }
                }
                if (ko == 1) {
                    Label cale = new Label();
                    cale.setText("* ");
               // group.getChildren().add(cale);
                    }
                else
                {
                    Label cale2 = new Label();
                    cale2.setText("O ");
                    //group.getChildren().add(cale2);
                    }
            }
            Label sout3 = new Label(" ");
            //group.getChildren().add(sout3);
        }
        Label sout2 = new Label(" ");
        //group.getChildren().add(sout2);

        Label rs = new Label("Radiatia minima pe care cercetatorul trebuie sa o primeasca este:  " + sum);
        group.getChildren().add(rs);
        ww.write(String.valueOf(sum));

        File file = new File("Radiatii.out");
        if (file.length() == -1)
        { Label gol = new Label("Din pacate nu am avut linii pare iar fisierul este gol.");
           group.getChildren().add(gol);

            }
        else
        { Label succes = new Label("Fisierul a primit date cu succes, puteti verifica!");
            group.getChildren().add(succes);
            }
        ww.close();

       group.setAlignment(Pos.CENTER);

        Button button = new Button("Back to menu");
        button.setFont(Font.font("Arial", FontWeight.MEDIUM, FontPosture.REGULAR, 14));

        button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    back(stage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        group.getChildren().add(button);
        Scene scene = new Scene(group, 400, 100);
        scene.getStylesheets().addAll(this.getClass().getResource("style2.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Sarcina 8");
        stage.show();
    }

    public void pressButton10(ActionEvent actionEvent) throws IOException {
        Vector<Vector<Short>> A;
        A = citesteFisier();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        VBox group = new VBox();

        TextField text = new TextField("Introdu suma careia vrei sa-i afli perechile!");


       // Label text = new Label();
        group.getChildren().add(text);
        group.setAlignment(Pos.CENTER);

        Button button = new Button("Click");
        button.setFont(Font.font("Arial", FontWeight.MEDIUM, FontPosture.REGULAR, 11));

        button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {

                Label tex = new Label("Perechile de 2 numere alaturate adunate care in suma dau " + text.getText() + " sunt: ");
                group.getChildren().add(tex);
               // System.out.println();
                Label tex1 = new Label("--------- Pe randuri ---------");
                group.getChildren().add(tex1);
                int nr = 0;
                for (Vector<Short> shorts : A) {
                    for (short j = 0; j < A.get(0).size() - 1; j++) {

                        if ((shorts.get(j) + shorts.get(j + 1) == Integer.parseInt(text.getText()))) {
                            Label tex2 = new Label(shorts.get(j) + " si " + shorts.get(j + 1) + " adunate vor da " + text.getText());
                            group.getChildren().add(tex2);
                            nr++; }
                    }
                }
                if(nr==0) { Label texx = new Label("Nu avem perechi de numere alaturate egale cu " + text.getText());
                    group.getChildren().add(texx);
                }
               // System.out.println();

                Label tex4 = new Label("--------- Pe coloane ---------");
                group.getChildren().add(tex4);
                nr = 0;
                for (short i = 0; i < A.get(0).size(); i++) {
                    for (short j = 0; j < A.size() - 1; j++) {
                        if (A.get(j).get(i) + A.get(j + 1).get(i) == Integer.parseInt(text.getText())) {
                            Label tex3 = new Label(A.get(j).get(i) + " si " + A.get(j + 1).get(i) + " adunate vor da " + Integer.parseInt(text.getText()));
                            group.getChildren().add(tex3);
                            nr++; }
                    }
                }
                if(nr==0) {
                    Label texx2 = new Label("Nu avem perechi de numere alaturate egale cu " + Integer.parseInt(text.getText()));
                    group.getChildren().add(texx2);

                }
                Label texw = new Label(" \n");
                group.getChildren().add(texw);
                Button button2 = new Button("Back to menu");
                button2.setFont(Font.font("Helvica", FontWeight.EXTRA_LIGHT, FontPosture.ITALIC, 14));

                button2.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            back(stage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                group.getChildren().add(button2);
                button.setVisible(false);
                text.setVisible(false);
            }


        });

        group.getChildren().add(button);
        Label texw = new Label(" \n");
        group.getChildren().add(texw);


        Scene scene = new Scene(group, 400, 350);
        scene.getStylesheets().addAll(this.getClass().getResource("style2.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Sarcina adaugatoare");
        stage.show();

    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
        exit(actionEvent);
    }
}
class ConsoleColors {
    public static final String RESET = "\033[0m";  // Text Reset
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";
}
