package com.example.mathapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mathapp.authentication.LoginActivity
import com.example.mathapp.challenges.classes.Answer
import com.example.mathapp.challenges.classes.Answers
import com.example.mathapp.challenges.classes.Category
import com.example.mathapp.challenges.classes.Question
import com.example.mathapp.challenges.classes.lengthquiz.DifficultyLevels
import com.example.mathapp.challenges.classes.lengthquiz.LengthOfTheQuiz
import com.example.mathapp.challenges.classes.lengthquiz.QuizSettings
import com.example.mathapp.science.classes.Module
import com.example.mathapp.science.classes.Paragraph
import com.example.mathapp.science.classes.PictureLocalization
import com.example.mathapp.science.classes.Section
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.start_activity.*
import java.util.*
import kotlin.collections.ArrayList

class StartActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)

        val time = Date().time

        val module = Module(time.toString(), "Zbiory", arrayListOf(),
            arrayListOf(
                Section("0", "",
                    arrayListOf(
                        Paragraph("0", "text", "Zbiory oznaczamy wielkimi literami, w celu opisania zbioru należy określić jakie " +
                                "są jego elementy np. \n" +
                                "N = {0,1,2,3,4,5,6,7...} - zbiór liczb naturanlnych \n" +
                                "D = {1,3,6,9,18} - zbiór naturalnych dzielników liczby 18\n"
                        ),
//                        Paragraph("1", "image",
//                            PictureLocalization("https://firebasestorage.googleapis.com/v0/b/mathapp-74bce.appspot.com/o/logo.png?alt=media&token=66db2991-f726-414a-bd0e-bac2624edbf8")),
                        Paragraph("1", "text", "Zbiór, który ma skończoną liczbę elementów, nazywamy zbiorem skończonym." +
                                "Zbiór, do którego należy nieskończenie wiele elementów, nazywamy zbiorem" +
                                "nieskończonym.\n"),
                        Paragraph("2", "text", "Aby zapisać, że element należy do zbioru, używamy symbolu ∈, np. 7 ∈ N," +
                                "aby zapisać, że element nie należy do zbioru — symbolu £, np. V2 £ W." +
                                "Zbiór, do którego nie należy żaden element, nazywamy zbiorem pustym i ozna-" +
                                "czamy symbolem f.\n")
                    )
                    ),
                Section("1", "Przykład:",
                    arrayListOf(
                        Paragraph("0", "text",
                                "Wypisz wszystkie elementy zbiorów: D, E i F.\n" +
                                "a) D - zbiór tych liczb naturalnych, których kwadraty są mniejsze od 5\n" +
                                "D = { 0,1,2}\n" +
                                "b) E - zbiór tych liczb całkowitych, których kwadraty są mniejsze od 5\n" +
                                "E = { -2,-1,0,1,2}\n")
                    )
                    ),
                Section("2", "DEFINICJA:",
                    arrayListOf(
                        Paragraph("0", "text",
                                "Dwa zbiory są równe wtedy i tylko wtedy gdy maja te same elementy.\n"),

                        )
                    ),
                Section("3", "Przykład:",
                    arrayListOf(
                        Paragraph("0", "text",
                                "Zbiory A i B są równe w obu przypadkach\n" +
                                "A= {-6,-3,0,3,6}\n" +
                                "B= {0,3,-3,6,-6}\n\n" +
                                "A= {-3,-2,-1,-0,1,2,3}\n" +
                                "B - zbiór tych liczb całkowitych których kwadraty są mniejsze od 10\n"),

                        )
                    ),
                Section("4", "DEFINICJA:",
                    arrayListOf(
                        Paragraph("0", "text",
                                "Zbiór A jest podzbiorem zbioru B, jeśli każdy element zbioru A jest ele-" +
                                "mentem zbioru B. Zapisujemy to: A ⊂ B. Mówimy również, że zbiór A" +
                                "jest zawarty w zbiorze B.\n" +
                                "Zapis A ⊄ B oznacza, że A nie jest podzbiorem zbioru B.\n")

                    )
                    ),
                Section("5", "DEFINICJA:",
                    arrayListOf(
                        Paragraph("0", "text",
                                "Iloczynem zbiorów A i B nazywamy zbiór elementów, które należą " +
                                "jednocześnie do oby tch zbiórów. Iloczyn zbiorów A i B oznaczamy A ∩ B.\n")
                    )
                    ),
                Section("6", "Przykład:",
                    arrayListOf(
                        Paragraph("0", "text",
                                    "Niech A = {1,2,3,4}, B = {0,2,4,6,8,10}. Tylko liczby 2 i 4 należą do obu" +
                                    "zbiorów jednocześnie, zatem:\n\n" +
                                    "A ∩ B = (2,4)\n")
                    )
                    ),
                Section("7", "DEFINICJA:",
                    arrayListOf(
                        Paragraph("0", "text",
                                "Sumą zbiorów A i B nazywyamy zbiór elementów, które należa do conajmniej" +
                                "jednego ze zbiorów:A lub B Sumę zbiorów A i B onzaczamy A ∪ B\n"
                        )
                    )
                    ),
                Section("8", "Przykład:",
                    arrayListOf(
                        Paragraph("0", "text",
                                "Jeśli A ={ 2,3,4} i B={1,3,5,7} to A ∪ B = {1,2,3,4,5,7}\n")
                    )
                    ),
                Section("9", "DEFINICJA:",
                    arrayListOf(
                        Paragraph("0", "text",
                                "Róznicą zbiorów A i B nazywyamy zbiór elementów, które należa do zbioru" +
                                "A i nie należa do zbioru B. Różnice zbiórów A i B oznaczamy A\\B.\n")
                    )
                    ),
                Section("10", "Przykład:",
                    arrayListOf(
                        Paragraph("0", "text",
                                "Jeśli A ={ 2,3,4,5} i B={2,3} to A \\ B = {4,5}.\n")

                    )
                    )

            )
        )


        val module3 = Module(time.toString(), "Liczby naturalne",
            arrayListOf(),
            arrayListOf(
                Section("0", "",
                arrayListOf(
                    Paragraph("0", "text","Liczb naturalnych: 0,1,2,3,... jest nieskończenie wiele. Dla dowolnej liczby" +
                            "naturalnej n liczba n +1 jest następna (większa o 1) i tak po milionie następuje" +
                            "milion jeden, potem milion dwa, milion trzy, zaś po bilionie następuje bilion" +
                            "jeden itd. Zbiór liczb naturalnych oznaczamy literą <b>N</b>.\n")

                )),
                Section("1", "DEFINICJA:",
                arrayListOf(
                    Paragraph("0", "text", "Liczbę naturalną m ≠ 0 nazywamy <b>dzielnikiem</b> liczby naturalnej n, gdy" +
                            "iloraz n : m jest liczbą naturalną.\n"),
                    Paragraph("1", "text", "Liczba 12 ma następujące dzielniki: 1, 2, 3, 4, 6, 12." +
                            "Zauważ, że:\n"),
                    Paragraph("2", "text", "- liczba 1 jest dzielnikiem każdej liczby naturalnej,"),
                    Paragraph("3", "text", "- liczba 0 nie jest dzielnikiem żadnej liczby,"),
                    Paragraph("4", "text", "każda dodatnia liczba naturalna jest dzielnikiem liczby 0.")

                )),
                Section("2", "CECHY PODZIELNOŚCI: ",
                    arrayListOf(
                        Paragraph("0", "text", "Liczba naturalna jest podzielna przez:\n"),
                        Paragraph("1", "text", "* 2, gdy ostatnią jej cyfrą jest jedna z cyfr: 0, 2, 4, 6,\n"),
                        Paragraph("2", "text", "* 3, gdy suma jej cyfr jest podzielna przez 3;\n"),
                        Paragraph("3", "text", "* 5, gdy ostatnią jej cyfrą jest 0 lub 5;\n"),
                        Paragraph("4", "text", "* 9, gdy suma jej cyfr jest podzielna przez 9.\n")

                    )
                    ),
                Section("3", "",
                    arrayListOf(
                        Paragraph("0", "text",
                                "\n\nLiczbę naturalną nazywamy parzystą, gdy jest ona podzielna przez 2, lub" +
                                "nieparzystą, gdy nie jest podzielna przez 2. Zero jest liczbą parzystą.\n")

                    )
                    )


            )


        )




//        val module = Module(time.toString(), "Zbiory", arrayListOf(),
//        arrayListOf(
//            Paragraph("0", "text", "Zbiory oznaczamy wielkimi literami, w celu opisania zbioru należy określić jakie " +
//                    "są jego elementy np. \n" +
//                    "N = {0,1,2,3,4,5,6,7...} - zbiór liczb naturanlnych \n" +
//                    "D = {1,3,6,9,18} - zbiór naturalnych dzielników liczby 18\n"
//                            ),
//            Paragraph("1", "image",
//                PictureLocalization("https://firebasestorage.googleapis.com/v0/b/mathapp-74bce.appspot.com/o/logo.png?alt=media&token=66db2991-f726-414a-bd0e-bac2624edbf8")),
//            Paragraph("2", "text", "Zbiór, który ma skończoną liczbę elementów, nazywamy zbiorem skończonym." +
//                    "Zbiór, do którego należy nieskończenie wiele elementów, nazywamy zbiorem" +
//                    "nieskończonym.\n"),
//            Paragraph("3", "text", "Aby zapisać, że element należy do zbioru, używamy symbolu ∈, np. 7 ∈ N," +
//                    "aby zapisać, że element nie należy do zbioru — symbolu £, np. V2 £ W." +
//                    "Zbiór, do którego nie należy żaden element, nazywamy zbiorem pustym i ozna-" +
//                    "czamy symbolem f.\n"),
//            Paragraph("4", "text", "Przykład:\n" +
//                    "Wypisz wszystkie elementy zbiorów: D, E i F.\n" +
//                    "a) D - zbiór tych liczb naturalnych, których kwadraty są mniejsze od 5\n" +
//                    "D = { 0,1,2}\n" +
//                    "b) E - zbiór tych liczb całkowitych, których kwadraty są mniejsze od 5\n" +
//                    "E = { -2,-1,0,1,2}\n"),
//            Paragraph("5", "text", "DEFINICJA.\n" +
//                    "Dwa zbiory są równe wtedy i tylko wtedy gdy maja te same elementy.\n"),
//            Paragraph("6", "text", "Przykład\n" +
//                    "Zbiory A i B są równe w obu przypadkach\n" +
//                    "A= {-6,-3,0,3,6}\n" +
//                    "B= {0,3,-3,6,-6}\n\n" +
//                    "A= {-3,-2,-1,-0,1,2,3}\n" +
//                    "B - zbiór tych liczb całkowitych których kwadraty są mniejsze od 10\n"),
//            Paragraph("7", "text","DEFINICJA.\n" +
//                    "Zbiór A jest podzbiorem zbioru B, jeśli każdy element zbioru A jest ele-" +
//                    "mentem zbioru B. Zapisujemy to: A ⊂ B. Mówimy również, że zbiór A" +
//                    "jest zawarty w zbiorze B.\n" +
//                    "Zapis A ⊄ B oznacza, że A nie jest podzbiorem zbioru B.\n"),
//            Paragraph("8", "text", "DEFINICJA.\n" +
//                    "Iloczynem zbiorów A i B nazywamy zbiór elementów, które należą " +
//                    "jednocześnie do oby tch zbiórów. Iloczyn zbiorów A i B oznaczamy A ∩ B.\n"),
//            Paragraph("9", "text",
//                    "Przyklad: \n" +
//                    "Niech A = {1,2,3,4}, B = {0,2,4,6,8,10}. Tylko liczby 2 i 4 należą do obu" +
//                    "zbiorów jednocześnie, zatem:\n\n" +
//                            "A ∩ B = (2,4)\n"),
//            Paragraph("10", "text", "DEFINICJA.\n" +
//                    "Sumą zbiorów A i B nazywyamy zbiór elementów, które należa do conajmniej" +
//                    "jednego ze zbiorów:A lub B Sumę zbiorów A i B onzaczamy A ∪ B\n"
//                    ),
//            Paragraph("11", "text", "Przykład:\n" +
//                    "Jeśli A ={ 2,3,4} i B={1,3,5,7} to A ∪ B = {1,2,3,4,5,7}\n"),
//            Paragraph("12", "text", "DEFINICJA\n" +
//                    "Róznicą zbiorów A i B nazywyamy zbiór elementów, które należa do zbioru" +
//                    "A i nie należa do zbioru B. Różnice zbiórów A i B oznaczamy A\\B.\n"),
//            Paragraph("13", "text", "Przykład\n" +
//                    "Jeśli A ={ 2,3,4,5} i B={2,3} to A \\ B = {4,5}.\n")
//
//        ))

//        val module1 = Module(
//            time.toString(),
//            "Całki",
//            arrayListOf(),
//            arrayListOf(
//                Paragraph("0", "text", "Przygładowy tekst nowego modułu.\n"),
//                Paragraph("1", "text", "Ostatni akapit modułu.\n")
//
//            )
//
//
//        )
        val category3 = Category(
            time.toString(), "Zbiory",
            arrayListOf(
                Question("0", "Wskaż poprawne zależności pomiędzy zbiorami A, B i C A=(1,2,3,4,5,6,7} B={1,2,3} C={6,7}",
                    Answers(
                        Answer("B ⊂ A, C ⊂ B"),
                        Answer("B ⊂ A, C ⊂ A"),
                        Answer("C ⊂ A, A ⊂ B"),
                        Answer("A ⊂ B, C ⊂ B"),
                        Answer("B ⊂ A, C ⊂ A")
                    ),
                    "1"
                ),


                Question("1", "Wskaz poprawne stwierdzenie dla zbiorów A=(1,6,7} B={1,6,7,8}",
                    Answers(
                        Answer("B jest podzbiorem A"),
                        Answer("A ∩ B = (1,6)"),
                        Answer("A jest podzbiorem B"),
                        Answer("A = B"),
                        Answer("A jest podzbiorem B")

                    ),
                    "1"
                ),


                Question("2", "Ktore zbiory spełniają zależnośc A ⊂ B?",
                    Answers(
                        Answer("A=(1,2,3,4,5,6,7}, C={1,2,3}"),
                        Answer("A={3,4,5,6,7}, B={1,2,3}"),
                        Answer("A={3,7}, B={3,4,5,6,7}"),
                        Answer("A= {1,2}, B={3,8,5}"),
                        Answer("A={3,7}, B={3,4,5,6,7}")

                    ),
                    "1"
                ),


                Question("3", "Wskaż Zbiór równy zbiorowi A= {-3,-2,-1,0,1,2,3}",
                    Answers(
                        Answer("Zbiór liczb naturalnych których kwadraty są mniejsze od 10."),
                        Answer("Zbiór liczb całkowitych których kwadraty są mniejsze od 10."),
                        Answer("Zbiór liczb nieujemnych których kwadraty są mniejsze od 10."),
                        Answer("Zbiór liczb naturalnych."),
                        Answer("Zbiór liczb całkowitych których kwadraty są mniejsze od 10.")

                    ),
                    "1"
                ),


                Question("4", "Suma zbiorów A={2,3,6,7,11} i B= {5,8,2,6,11,9} to zbiór:",
                    Answers(
                        Answer("{2,2,3,5,6,6,7,8,9,11,11}"),
                        Answer("{2,3,5,6,7,8,9,11}"),
                        Answer("{2,6}"),
                        Answer("{2,6,11}"),
                        Answer("{2,3,5,6,7,8,9,11}")

                    ),
                    "1"
                ),


                Question("5", "Suma tych zbiorów to {1,2,5}, wskaż poprawne zbiory A i B",
                    Answers(
                        Answer("A={1,2,3,4,5}, B={4,5}"),
                        Answer("A={1}, B={2}, C={5}"),
                        Answer("A={1,2,5}, B={1,5}"),
                        Answer("A={6,4,5}, B={5,3,0}"),
                        Answer("A={1,2,5}, B={1,5}")

                    ),
                    "1"
                ),


                Question("6", "Iloczyn tych zbiorów to {4,5}, wskaż poprawne zbiory A i B",
                    Answers(
                        Answer("A={1,2,3,4,5}, B={4,5}"),
                        Answer("B={4,5,6}, C={4,5}"),
                        Answer("A={1,2,5}, B={1,5}"),
                        Answer("A={1,3,2}, B={5,3,7}"),
                        Answer("A={1,2,3,4,5}, B={4,5}")

                    ),
                    "1"
                ),


                Question("7", "Rożnica zbioru A\\B to {1,3,8,5}, wskaż poprawne zbiory A i B",
                    Answers(
                        Answer("A={1,2,3,4,5,6,7,8,9,10}, B={2,4,6,7,9,10}"),
                        Answer("A={1,3,8,5,0}, C={4,5}"),
                        Answer("A={1,3,8}, B={1,5}"),
                        Answer("A={1,3,2}, B={5,3,7}"),
                        Answer("A={1,2,3,4,5,6,7,8,9,10}, B={2,4,6,7,9,10}")

                    ),
                    "1"
                ),


                Question("8", "Suma tych zbiorów to {10,11,12}, wskaż poprawne zbiory A i B",
                    Answers(
                        Answer("A={10,11}, B={1,2,4,5}"),
                        Answer("A={10}, B={12}, C={11}"),
                        Answer("A={10,11,2}, B={12,5}"),
                        Answer("A={1,0,10,12}, B={5,6,7}"),
                        Answer("A={10,11,2} B={12,5}")

                    ),
                    "1"
                ),


                Question("9", "Iloczyn tych zbiorów to {10,11}, wskaż poprawne zbiory A i B",
                    Answers(
                        Answer("A={1,2,3,4,5,6,7,8,9,10,11,12,13}, B={2,3,4,5,6,7,8,9,1,0,11}"),
                        Answer("B={10,11,21}, C={11,12,22}"),
                        Answer("A={10,11,12}, B={1,5}"),
                        Answer("A={1,3,2,10,11}, B={1,2,3,4,5,6,7,8,9,10,11,12,13}"),
                        Answer("A={1,3,2,10,11}, B={1,2,3,4,5,6,7,8,9,10,11,12,13}")

                    ),
                    "1"
                ),
                Question("10", "Iloczyn zbiorów A={2,4,6,7,9,10} i B= {2,3,5,6,4} to zbiór:",
                    Answers(
                        Answer("{2,3,5,6,6,7,8,9,11,11}"),
                        Answer("{2,4,5,6,7,8,9,11}"),
                        Answer("{2,6}"),
                        Answer("{2,6,11}"),
                        Answer("{2,4,5,6,7,8,9,11}")

                    ),
                    "1"
                ),
                Question("11", "Zbiór A={2,3,6} jest podzbiorem zbioru B tylko kiedy",
                    Answers(
                        Answer("B={1,2,3,4,5}"),
                        Answer("B={1,2,3,4,7}"),
                        Answer("B={1,2,3,4,6}"),
                        Answer("B={2,3,5}"),
                        Answer("B={1,2,3,4,6}")

                    ),
                    "1"
                ),
                Question("12", "Zbiór A={1,2,3,4,6} jest podzbiorem zbioru B tylko kiedy",
                    Answers(
                        Answer("B={7,8,9,11,1,2,3,4,6}"),
                        Answer("B={1,2,3,4,7,8,9,11}"),
                        Answer("B={1,2,3,4}"),
                        Answer("B={2,3,5}"),
                        Answer("B={7,8,9,11,1,2,3,4,6}")

                    ),
                    "1"
                ),

                ),
            arrayListOf(
                Question("0", "Dla których wartosci zbiorów A i B jest prawdziwa zależność A ⊂ B",
                    Answers(
                        Answer("Zbiór dzielników liczby 63, zbiór dzielników liczby 36"),
                        Answer("Zbiór dzielników liczby 36, zbiór dzielników liczby 63"),
                        Answer("Zbiór dzielników liczby 36, zbiór dzielników liczby 72"),
                        Answer("Zbiór dzielników liczby 72, zbiór dzielników liczby 36"),
                        Answer("Zbiór dzielników liczby 36, zbiór dzielników liczby 72")
                    ),
                    "2"
                ),
                Question("1", "Dla których wartosci zbiorów A i B jest prawdziwa zależność A ∩ B = {3,4}",
                    Answers(
                        Answer("A = {π,4} B= {3,4}"),
                        Answer("A = {X,4} B= {3,4} gdzie x to drugi najmniejszy dzielnik liczby 471"),
                        Answer("A = {X,4} B= {1,0,3,4,7} gdzie x to drugi najmniejszy dzielnik liczby 103"),
                        Answer("A - zbiór dzielników liczby 28, B = {3,4}"),
                        Answer("A = {X,4} B= {3,4} gdzie x to drugi najmniejszy dzielnik liczby 471")
                    ),
                    "2"
                ),
                Question("2", "Dla których wartosci zbiorów A i B jest prawdziwa zależność B ⊂ A",
                    Answers(
                        Answer("A = {0,1,2,3}, B = {4,x} gdzie x to wynik dzielenia 100 przez 0"),
                        Answer("A = {0,1,2,3} B = {4,x} gdzie x to iloraz 100 przez 0"),
                        Answer("A = {0,1,2,3}, B = {4,x} gdzie x to wynik obliczenia 0*1*2*3*4+2-3+1"),
                        Answer("A = {0,1,2,3}, B = {4,x} gdzie x to wynik obliczenia 1*2-1"),
                        Answer("A = {0,1,2,3}, B = {4,x} gdzie x to wynik obliczenia 0*1*2*3*4+2-3+1")
                    ),
                    "2"
                ),
                Question("3", "Wskaż poprawną odpowiedź dla zbiorów A = { 1,2,3,4} B = {5,7,4}",
                    Answers(
                        Answer("A ∩ B ={4} i  A ⊄ B"),
                        Answer("B ⊂ A"),
                        Answer("A ⊂ B"),
                        Answer("A ∪ B ={4} A  B ⊄ A"),
                        Answer("A ∪ B ={4} A  B ⊄ A")
                    ),
                    "2"
                ),
                Question("4", "Wskaż poprawną odpowiedź dla zbiorów A = { 1,2,3,4} B = {5,4}",
                    Answers(
                        Answer("A ⊄ B bo zbior B tez posiada liczbę 4"),
                        Answer("B ⊄ A bo zbior B tez posiada liczbę 4"),
                        Answer("A ⊄ B bo zbior B ma większą liczbę"),
                        Answer("B ⊄ A bo wszystkie elementy zbioru nie znajdują sie w drugim zbiorze"),
                        Answer("B ⊄ A bo wszystkie elementy zbioru nie znajdują sie w drugim zbiorze")
                    ),
                    "2"
                ),


            ), //2
            arrayListOf(), //3
            arrayListOf(
                Question("0", "Zbiór A jest podzbiorem zbioru B...",
                    Answers(
                        Answer("Kiedy suma elementów zbioru A jest mniejsza od zbioru B"),
                        Answer("Każdy element zbioru B nalezy do zbioru A"),
                        Answer("Każdy element zbioru A nalezy do zbioru B"),
                        Answer("Zbiór B jest zawarty w zbiorze A"),
                        Answer("Każdy element zbioru A nalezy do zbioru B")
                    ),
                    "0"
                ),
                Question("1", "Wskaż poprawną odp,iloczyn zbiorów A i B to...",
                    Answers(
                        Answer("Wynik mnożenia elementów zbiorów przez siebie"),
                        Answer("Wynik dzielenia elementów zbiorów przez siebie"),
                        Answer("Zbiór elementów które należa do przynajmniej jednego ze zbiorów"),
                        Answer("Zbiór elementów które nalezy do jednocześnie obu zbiorów"),
                        Answer("Zbiór elementów które nalezy do jednocześnie obu zbiorów")
                    ),
                    "0"
                ),
                Question("2", "Wskaż poprawną odp, suma zbiorów A i B to...",
                    Answers(
                        Answer("Liczba bedąca wynikiem dodawania wszystkich elementów obu zbiorów"),
                        Answer("Wynik dzielenia elementów zbiorów przez siebie"),
                        Answer("Suma wszystkich elementów obu zbiorów"),
                        Answer("Zbiór elelementów które należa do conajmniej jednego ze zbiorów, wynik A + B"),
                        Answer("Zbiór elelementów które należa do conajmniej jednego ze zbiorów, wynik A + B")
                    ),
                    "0"
                ),
                Question("3", "Wskaż poprawną odp dotyczącą różnicy zbiorów A i B...",
                    Answers(
                        Answer("Zbiór elementów które należa do zbioru A i nie należa od zbioru B"),
                        Answer("Róznica oznaczana przez A - B"),
                        Answer("Wynik tego odejmowania nazywamy zbiorem C"),
                        Answer("Rożnica pomiedzy najwiekszym elementem zbioru A i zbioru B"),
                        Answer("Zbiór elementów które należa do zbioru A i nie należa od zbioru B")
                    ),
                    "0"
                ),
                Question("4", "Jakie zależności zachodzą pomiedzy zbiorami A = {1,2,3,4,5,6,7,8} B={1,3,5} C={2,7,8}",
                    Answers(
                        Answer("B ⊂ C"),
                        Answer("A ∩ B = {1,2,3,4,5}"),
                        Answer("A ⊂ B, A ⊂ C, A ∪ B = { 1,2,3,4,5,6,7,8}"),
                        Answer("B ⊂ A, C ⊂ A, A ∪ B = { 1,2,3,4,5,6,7,8}"),
                        Answer("B ⊂ A, C ⊂ A, A ∪ B = { 1,2,3,4,5,6,7,8}")
                    ),
                    "0"
                )



            ) //0


        )


        val category2 = Category(
            time.toString(), "Liczby naturalne",
            arrayListOf(
                Question("0", "Ile jest liczb naturalnych mniejszych od 28 podzielnych przez 3?",
                    Answers(
                        Answer("7"),
                        Answer("6"),
                        Answer("9"),
                        Answer("27"),
                        Answer("9")
                    ),
                    "1"
                ),


                Question("1", "Ile jest liczb naturalnych mniejszych od 250 podzielnych przez 30?",
                    Answers(
                        Answer("10"),
                        Answer("8"),
                        Answer("9"),
                        Answer("7"),
                        Answer("7")

                    ),
                    "1"
                ),


                Question("2", "Wskaż liczbe naturalną",
                    Answers(
                        Answer("0"),
                        Answer("-1"),
                        Answer("-0.5"),
                        Answer("-7.5"),
                        Answer("0")

                    ),
                    "1"
                ),


                Question("3", "Wskaż liczbe która nie jest naturalna",
                    Answers(
                        Answer("2"),
                        Answer("0"),
                        Answer("3"),
                        Answer("-1"),
                        Answer("-1")

                    ),
                    "1"
                ),


                Question("4", "Który ułamek jest nieskracalny?",
                    Answers(
                        Answer("10/12"),
                        Answer("13/17"),
                        Answer("20/5"),
                        Answer("15/3"),
                        Answer("13/17")

                    ),
                    "1"
                ),


                Question("5", "Skrócona wersja ułamku 225/360 to",
                    Answers(
                        Answer("25/72"),
                        Answer("25/80"),
                        Answer("5/8"),
                        Answer("38/60"),
                        Answer("25/72")

                    ),
                    "1"
                ),


                Question("6", "Wynik działania 3/4*5/7 to",
                    Answers(
                        Answer("15/28"),
                        Answer("15/11"),
                        Answer("28/15"),
                        Answer("2"),
                        Answer("15/28")

                    ),
                    "1"
                ),


                Question("7", "Wskaz poprawne rozszerzenie ułamka 3/8",
                    Answers(
                        Answer("24/56"),
                        Answer("8/3"),
                        Answer("25/60"),
                        Answer("30/72"),
                        Answer("24/56")

                    ),
                    "1"
                ),


                Question("8", "Przez ktora z tych liczb 60 nie jest podzielne?",
                    Answers(
                        Answer("6"),
                        Answer("10"),
                        Answer("5"),
                        Answer("8"),
                        Answer("8")

                    ),
                    "1"
                ),


                Question("9", "Przez ktora z tych liczb 40 jest podzielne?",
                    Answers(
                        Answer("3"),
                        Answer("16"),
                        Answer("8"),
                        Answer("7"),
                        Answer("8")

                    ),
                    "1"
                ),

            ), //1
            arrayListOf(), //2
            arrayListOf(), //3
            arrayListOf(
                Question("0", "Wskaż poprawną odpowiedź",
                    Answers(
                        Answer("Liczby naturalne są nieujemne"),
                        Answer("Liczb naturalnych jest skończona ilość"),
                        Answer("0 nie jest liczbą naturalną"),
                        Answer("każda liczba naturalna jest podzielna przez 1"),
                        Answer("Liczby naturalne są nieujemne")

                    ),
                    "0"
                ),
                Question("1", "Liczba naturalna jest podzielna przez 3 kiedy",
                    Answers(
                        Answer("Liczba jest nieparzysta"),
                        Answer("Ostatnia jej cyfrą jest 3,6 lub 9"),
                        Answer("Suma jej cyfr jest podzielna przez 3"),
                        Answer("Nie jest podzielna przez 2 i 5"),
                        Answer("Suma jej cyfr jest podzielna przez 3")

                    ),
                    "0"
                ),
                Question("2", "Jakie  nieujemne dzielniki ma liczba 36?",
                    Answers(
                        Answer("1,3,4,6,13"),
                        Answer("1,2,3,4,6,8,9,12,18,36"),
                        Answer("1,2,3,4,6,9,12,18,36"),
                        Answer("1,2,3,4,6,8,9,12,18,24,36"),
                        Answer("1,2,3,4,6,9,12,18,36")

                    ),
                    "0"
                ),
                Question("3", "Wskaż poprawną odpowiedź",
                    Answers(
                        Answer("Liczba 0 jest parzysta"),
                        Answer("Każda liczba parzysta jest podzielna przez 2"),
                        Answer("Jest wiecej nieparzystych cyfr niz parzystych"),
                        Answer("Jest wiecej parzystych cyfr niz nieparzystych"),
                        Answer("Liczba 0 jest parzysta")

                    ),
                    "0"
                ),
                Question("4", "Jakie dzielniki ma liczba 6?",
                    Answers(
                        Answer("1,2,3,6"),
                        Answer("1,2,3,6,-1,-2,-3,-6"),
                        Answer("-1,-2,-3,-6"),
                        Answer("-1,-2,-3,6"),
                        Answer("1,2,3,6,-1,-2,-3,-6")

                    ),
                    "0"
                ),


            ) // 0
        )

//        val category = Category(time.toString(), "First",
//        arrayListOf(
//            Question("0", "5+2",
//                Answers(
//                    Answer("4"),
//                    Answer("7"),
//                    Answer("10"),
//                    Answer("2"),
//                    Answer("7"),
//                )
//            ),
//            Question("1", "10*10",
//                Answers(
//                    Answer("1000"),
//                    Answer("100"),
//                    Answer("90"),
//                    Answer("150"),
//                    Answer("100"),
//
//
//                    )
//            ),
//            Question("2", "10-2",
//                Answers(
//                    Answer("5"),
//                    Answer("8"),
//                    Answer("12"),
//                    Answer("5"),
//                    Answer("8"),
//
//                )
//                ),
//            Question("3", "Wzór na kwadrat to:",
//                Answers(
//                    Answer("a*a"),
//                    Answer("a*b"),
//                    Answer("a+b*2"),
//                    Answer("b-a"),
//                    Answer("a*a"),
//
//                )
//                ),
//            Question("4", "Pytanie",
//                Answers(
//                    Answer("fałszywa odpowiedź"),
//                    Answer("fałszywa odpowiedź"),
//                    Answer("prawdziwa odpowiedź"),
//                    Answer("fałszywa odpowiedź"),
//                    Answer("prawdziwa odpowiedź"),
//
//                )
//                ),
//            Question("5", "Objętość prostokąta to:",
//                Answers(
//                    Answer("a*b"),
//                    Answer("a*a"),
//                    Answer("a+b"),
//                    Answer("2*a"),
//                    Answer("a*b"),
//
//                )
//                ),
//            Question("6", "2*1",
//                Answers(
//                    Answer("4"),
//                    Answer("6"),
//                    Answer("8"),
//                    Answer("2"),
//                    Answer("2"),
//
//                )
//                ),
//
//
//
//        ))

        val quizSettings: QuizSettings = QuizSettings(
            time.toString(),

                arrayListOf(
                    LengthOfTheQuiz(
                        "0",
                        "Łatwy",
                        "Krótki quiz, pięć losowych pytań.",
                        "5"
                    ),
                    LengthOfTheQuiz(
                        "1",
                        "Średni",
                        "Dziesięć losowych pytań do danego tematu.",
                        "10"
                    )


            ),
            Color.parseColor("#E4731B").toString()


        )


        val appInfo = LogoTextApp(
            "https://firebasestorage.googleapis.com/v0/b/mathapp-74bce.appspot.com/o/logo.png?alt=media&token=66db2991-f726-414a-bd0e-bac2624edbf8",
            "https://firebasestorage.googleapis.com/v0/b/mathapp-74bce.appspot.com/o/tekst.png?alt=media&token=312178d1-0d01-4986-bd9b-691700482e20"

        )

        val db = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
        //db.child("QuizSettings").child(time.toString()).setValue(quizSettings)
        //db.child("AppInfo").setValue(appInfo)

        var dbReference = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
        //dbReference.child("Modules").child(time.toString()).setValue(module)
        //dbReference.child("Modules").child(time.toString()).setValue(module)
        //dbReference.child("questionsCategories").child(category3.id).setValue(category3)





        var query = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
        query.child("AppInfo").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists())
                {

                    var appInfor = LogoTextApp()

                    for (ai in snapshot.children)
                    {

                        if (ai.key == "logo")
                        {
                            appInfor.logo = ai.value.toString()
                        }else if(ai.key == "text")
                        {
                            appInfor.text = ai.value.toString()
                        }

                        //val appInfor = ai.getValue(LogoTextApp::class.java)!!

                    }

                    Picasso.get().load(appInfor!!.logo).into(image_logo)
                    Picasso.get().load(appInfor!!.text).into(image_text)



//                    Picasso.get().load(appInfor.logo.localization).into(image_logo)
//
                }

                //var link: String? = snapshot.getValue(String::class.java)



            }

            override fun onCancelled(error: DatabaseError) {

            }

        })



        start_btn.setOnClickListener{
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)




        }

    }
}