package at.fh.swengb.loggingviewsandactivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_lesson_list.*


class LessonListActivity : AppCompatActivity() {
    val lessonAdapter = LessonAdapter() {
        Toast.makeText(this, "Lesson with name: ${it.name} has been clicked", Toast.LENGTH_LONG)
            .show()
        val intent = Intent(this, LessonRatingActivity::class.java)
        intent.putExtra(EXTRA_LESSON_ID, it.id)
        startActivityForResult(intent, ADD_OR_EDITED_RESULT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson_list)

        lesson_recycler_view.layoutManager =
            LinearLayoutManager(this)// as RecyclerView.LayoutManager?
        lesson_recycler_view.adapter = lessonAdapter

        parseJson()
        SleepyAsyncTask().execute()

        updatelist()
    }

    private fun updatelist() {
        LessonRepository.lessonsList(
            success = {
                lessonAdapter.updateList(it) // handle success
            },
            error = {
                Toast.makeText(this, "No list found", Toast.LENGTH_SHORT).show()
                finish()
            }
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == ADD_OR_EDITED_RESULT) {
            updatelist()
            //Log.e("HALLO", "hallooo")
        }
    }

    companion object {
        val EXTRA_LESSON_ID = "LESSON_ID_EXTRA"
        val ADD_OR_EDITED_RESULT = 1;
    }


    fun parseJson() {

        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter<Lesson>(Lesson::class.java)
        val json = (
            """
            {
                "id": "1",
                "name": "Lecture 0",
                "date": "09.10.2019",
                "topic": "Introduction",
                "type": "LECTURE",
                "lecturers": [
                    {
                        "name": "Lukas Bloder"
                    },
                    {
                        "name": "Sanja Illes"
                    }
                ],
                "ratings": []
            }
        """)
        val result = jsonAdapter.fromJson(json)
        Log.e("moshi",result!!.name)
    }
}