package com.tech.studmsystem

import android.content.ContentValues
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
/*
-create a spash screen.
-dialog for deleting details.
-delete as per names.
-sort student details as per names
-provide validations to edittext
-set app icon
 */


        //step 1: create database
        var sqLiteDatabase = openOrCreateDatabase("DB_10_SEPT", Context.MODE_PRIVATE, null)

        //step 2: create tables.

        sqLiteDatabase.execSQL("create table if not exists studentinfo(_id INTEGER PRIMARY KEY AUTOINCREMENT ,sroll Integer,sname varchar(25),squal varchar(10),smarks varchar(10))")

        //insert() ,query() ,update(),delete()

        btn_insert.setOnClickListener {

            var roll_number = et_roll.text.toString()
            var student_name = et_name.text.toString()
            var s_qualification = et_qual.text.toString()
            var student_marks = et_marks.text.toString()

            var cv = ContentValues()
            cv.put("sroll", roll_number)
            cv.put("sname", student_name)
            cv.put("squal", s_qualification)
            cv.put("smarks", student_marks)

            var status = sqLiteDatabase.insert("studentinfo", null, cv)
            if (status == (-1).toLong()) {
                Toast.makeText(this, "unable to insert the data", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "data inserted successfully", Toast.LENGTH_SHORT).show()
            }

        }


        //Cursor Adapter
        btn_read.setOnClickListener {

            var cursor = sqLiteDatabase.query("studentinfo", null, null, null, null, null, null)

            var fromArray = arrayOf("sroll", "sname", "squal", "smarks")
            var intArray = intArrayOf(R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4)

            var cursor_adapter = SimpleCursorAdapter(
                this,
                R.layout.my_view_student_details,
                cursor,
                fromArray,
                intArray
            )
            list_view.adapter = cursor_adapter

        }
        btn_update.setOnClickListener {
            var roll_number = et_roll.text.toString()
            var student_name = et_name.text.toString()
            var s_qualification = et_qual.text.toString()
            var student_marks = et_marks.text.toString()

            var cv = ContentValues()
            cv.put("sroll", roll_number)
            cv.put("sname", student_name)
            cv.put("squal", s_qualification)
            cv.put("smarks", student_marks)

            var status = sqLiteDatabase.update("studentinfo", cv, "sroll=?", arrayOf(roll_number))
            if (status > 0) {
                Toast.makeText(this, "data updated successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "failed to update the data", Toast.LENGTH_SHORT).show()
            }
        }

        btn_delete.setOnClickListener {
            var roll_number = et_roll.text.toString()
            var status = sqLiteDatabase.delete("studentinfo", "sroll=?", arrayOf(roll_number))
            if (status > 0) {

                Toast.makeText(this, "data deleted successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "failed to delete the data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}