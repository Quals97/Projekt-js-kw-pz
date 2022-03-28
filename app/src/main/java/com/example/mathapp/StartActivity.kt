package com.example.mathapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mathapp.authentication.LoginActivity
import com.example.mathapp.challenges.classes.Answer
import com.example.mathapp.challenges.classes.Answers
import com.example.mathapp.challenges.classes.Category
import com.example.mathapp.challenges.classes.Question
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

        val category = Category(time.toString(), "First",
        arrayListOf(
            Question("0", "5+2",
                Answers(
                    Answer("4"),
                    Answer("7"),
                    Answer("10"),
                    Answer("2"),
                    Answer("7"),
                )
            ),
            Question("1", "10*10",
                Answers(
                    Answer("1000"),
                    Answer("100"),
                    Answer("90"),
                    Answer("150"),
                    Answer("100"),


                    )
            )



        ))

        val questionsList: ArrayList<Question> = arrayListOf(
            Question("0", "5+2",
                Answers(
                    Answer("4"),
                    Answer("7"),
                    Answer("10"),
                    Answer("2"),
                    Answer("7"),
                )
            ),
            Question("1", "10*10",
                Answers(
                    Answer("1000"),
                    Answer("100"),
                    Answer("90"),
                    Answer("150"),
                    Answer("100"),
                )
                )


        )

        val question: Question = Question("0", "5+2",
            Answers(
                Answer("4"),
                Answer("7"),
                Answer("10"),
                Answer("2"),
                Answer("7"),
            )
            )

        val appInfo = LogoTextApp(
            "https://firebasestorage.googleapis.com/v0/b/mathapp-74bce.appspot.com/o/logo.png?alt=media&token=66db2991-f726-414a-bd0e-bac2624edbf8",
            "https://firebasestorage.googleapis.com/v0/b/mathapp-74bce.appspot.com/o/tekst.png?alt=media&token=312178d1-0d01-4986-bd9b-691700482e20"

        )

        val db = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
        db.child("AppInfo").setValue(appInfo)

        var dbReference = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
        //dbReference.child("Modules").child(time.toString()).setValue(module)
        dbReference.child("Modules").child(time.toString()).setValue(module)
        //dbReference.child("questionsCategories").child(category.id).setValue(category)





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