package com.example.mathapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mathapp.authentication.LoginActivity
import com.example.mathapp.challenges.classes.Answer
import com.example.mathapp.challenges.classes.Answers
import com.example.mathapp.challenges.classes.Question
import com.example.mathapp.science.classes.Module
import com.example.mathapp.science.classes.Paragraph
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
            Paragraph("0", "Zbiory oznaczamy wielkimi literami, w celu opisania zbioru należy określić jakie " +
                    "są jego elementy np. \n" +
                    "N = {0,1,2,3,4,5,6,7...} - zbiór liczb naturanlnych \n" +
                    "D = {1,3,6,9,18} - zbiór naturalnych dzielników liczby 18\n"
                            ),
            Paragraph("1", "Zbiór, który ma skończoną liczbę elementów, nazywamy zbiorem skończonym." +
                    "Zbiór, do którego należy nieskończenie wiele elementów, nazywamy zbiorem" +
                    "nieskończonym.\n"),
            Paragraph("2", "Aby zapisać, że element należy do zbioru, używamy symbolu ∈, np. 7 ∈ N," +
                    "aby zapisać, że element nie należy do zbioru — symbolu £, np. V2 £ W." +
                    "Zbiór, do którego nie należy żaden element, nazywamy zbiorem pustym i ozna-" +
                    "czamy symbolem f.\n"),
            Paragraph("3", "Przykład:\n" +
                    "Wypisz wszystkie elementy zbiorów: D, E i F.\n" +
                    "a) D - zbiór tych liczb naturalnych, których kwadraty są mniejsze od 5\n" +
                    "D = { 0,1,2}\n" +
                    "b) E - zbiór tych liczb całkowitych, których kwadraty są mniejsze od 5\n" +
                    "E = { -2,-1,0,1,2}\n"),
            Paragraph("4", "DEFINICJA.\n" +
                    "Dwa zbiory są równe wtedy i tylko wtedy gdy maja te same elementy.\n"),
            Paragraph("5", "Przykład\n" +
                    "Zbiory A i B są równe w obu przypadkach\n" +
                    "A= {-6,-3,0,3,6}\n" +
                    "B= {0,3,-3,6,-6}\n\n" +
                    "A= {-3,-2,-1,-0,1,2,3}\n" +
                    "B - zbiór tych liczb całkowitych których kwadraty są mniejsze od 10\n"),
            Paragraph("6","DEFINICJA.\n" +
                    "Zbiór A jest podzbiorem zbioru B, jeśli każdy element zbioru A jest ele-" +
                    "mentem zbioru B. Zapisujemy to: A ⊂ B. Mówimy również, że zbiór A" +
                    "jest zawarty w zbiorze B.\n" +
                    "Zapis A ⊄ B oznacza, że A nie jest podzbiorem zbioru B.\n"),
            Paragraph("7", "DEFINICJA.\n" +
                    "Iloczynem zbiorów A i B nazywamy zbiór elementów, które należą " +
                    "jednocześnie do oby tch zbiórów. Iloczyn zbiorów A i B oznaczamy A ∩ B.\n"),
            Paragraph("8",
                    "Przyklad: \n" +
                    "Niech A = {1,2,3,4}, B = {0,2,4,6,8,10}. Tylko liczby 2 i 4 należą do obu" +
                    "zbiorów jednocześnie, zatem:\n\n" +
                            "A ∩ B = (2,4)\n"),
            Paragraph("9", "DEFINICJA.\n" +
                    "Sumą zbiorów A i B nazywyamy zbiór elementów, które należa do conajmniej" +
                    "jednego ze zbiorów:A lub B Sumę zbiorów A i B onzaczamy A ∪ B\n"
                    ),
            Paragraph("10", "Przykład:\n" +
                    "Jeśli A ={ 2,3,4} i B={1,3,5,7} to A ∪ B = {1,2,3,4,5,7}\n"),
            Paragraph("11", "DEFINICJA\n" +
                    "Róznicą zbiorów A i B nazywyamy zbiór elementów, które należa do zbioru" +
                    "A i nie należa do zbioru B. Różnice zbiórów A i B oznaczamy A\\B.\n"),
            Paragraph("12", "Przykład\n" +
                    "Jeśli A ={ 2,3,4,5} i B={2,3} to A \\ B = {4,5}.\n")

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


        var dbReference = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
       //dbReference.child("Modules").child(time.toString()).setValue(module)
        dbReference.child("Questions").setValue(questionsList)





        var query = FirebaseDatabase.getInstance("https://mathapp-74bce-default-rtdb.europe-west1.firebasedatabase.app/").reference
        query.child("logo").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var link: String? = snapshot.getValue(String::class.java)
                println("LINK: ${link}")
                Picasso.get().load(link).into(image_logo)
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