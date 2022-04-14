package com.example.databaseapp

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var spStars: Spinner
    private lateinit var etAchiv: EditText
    private lateinit var etNumber: EditText
    private lateinit var etFind: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnZapros1: Button
    private lateinit var btnZapros2: Button
    private lateinit var btnZapros3: Button
    private lateinit var btnZapros4: Button
    private var hl:HostelModel? = null

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: HostelAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        //вызов функции инициалиации
        initView()
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)

        //Слушатель на кнопку добавления
        btnAdd.setOnClickListener() { addHostel() }
        btnView.setOnClickListener() { getHostels() }
        btnZapros2.setOnClickListener() { getHostelsMaxMinNumber() }
        btnZapros3.setOnClickListener() { getHostelEstNumb() }
        btnZapros4.setOnClickListener() { getHostelAlf() }
        btnZapros1.setOnClickListener() { getHostelsStars() }

        adapter?.setOnClickDeleteItem { deleteHostel(it.name) }
        adapter?.setOnClickChangeItem { updateHostel(it.name, it.stars, it.estimation, it.number) }

        etFind.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

                val HlList = sqLiteHelper.getHostelName(s.toString())
                Log.e("pppp", "${HlList.size}")

                adapter?.addItems(HlList)
            }
        })
    }

    //функция добавления новой гостиницы
    private fun addHostel() {
        //Получаем данные с формы
        val name: String = etName.text.toString()
        val stars: Int = spStars.selectedItemPosition + 1
        val estimation: Double = (etAchiv.text.toString()).toDouble()
        val number: Int = (etNumber.text.toString()).toInt()

        //Если данных нет
        if (name.isEmpty() || estimation == 0.0 || number == 0) {
            Toast.makeText(this,"Пожалуйста, заполните поля!", Toast.LENGTH_SHORT).show()
        } else {
            val Hl = HostelModel(name = name, stars = stars, estimation = estimation, number = number)
            val status = sqLiteHelper.insertHostel(Hl)
            //Проверка вставки в БД
            if (status > -1){
                Toast.makeText(this, "Гостиница успешно добавлена!", Toast.LENGTH_SHORT).show()
                clearForms()
                getHostels()
            } else {
                Toast.makeText(this,"Данные не сохранены", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Функция возращающая все записи из БД
    private fun getHostels() {
        val HlList = sqLiteHelper.getAllHostel()
        Log.e("pppp", "${HlList.size}")

        adapter?.addItems(HlList)
    }

    //Функция, которая возвращает записи отсортированные по убыванию числу номеров
    private fun getHostelsMaxMinNumber() {
        val HlList = sqLiteHelper.getHostelNumberMaxMin()
        Log.e("pppp", "${HlList.size}")

        adapter?.addItems(HlList)
    }

    //Функция, которая возращает записи по выставленному значению звезд
    private fun getHostelsStars() {
        val HlList = sqLiteHelper.getHostelStars((spStars.selectedItemPosition + 1))
        Log.e("pppp", "${HlList.size}")

        adapter?.addItems(HlList)
    }

    //Функция получения гостиниц в алфавитном порядке
    private fun getHostelAlf() {
        val HlList = sqLiteHelper.getHostelAlf()
        Log.e("pppp", "${HlList.size}")

        adapter?.addItems(HlList)
    }

    private fun getHostelEstNumb() {
        val HlList = sqLiteHelper.getHostelEstNumb()
        Log.e("pppp", "${HlList.size}")

        adapter?.addItems(HlList)
    }

    //Функция удаления гостиницы
    private fun deleteHostel(name: String){
        if(name.isEmpty()) return

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Вы уверены, что хотите удалить запись о $name ?")
        builder.setCancelable(true)
        builder.setPositiveButton("Да, удалить") {dialog, _ ->
            sqLiteHelper.deleteHostelBuName(name)
            getHostels()
            dialog.dismiss()
        }
        builder.setNegativeButton("Нет, отменить") {dialog, _ ->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }

    private fun getIndex(spinner: Spinner, myString: String): Int {
        var index = 0
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).equals(myString)) {
                index = i
            }
        }
        return index
    }

    private fun updateHostel(name: String, stars: Int, estimation: Double, number: Int){
        if(name.isEmpty()) return

        val dialog = Dialog(this)
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.custom_layout_dialog)
        val name_dialog = dialog.findViewById(R.id.et_name_dialog) as EditText
        name_dialog.setText(name)
        val stars_dialog = dialog.findViewById(R.id.sp_star_dialog) as Spinner
        stars_dialog.setSelection(getIndex(stars_dialog, stars.toString()));
        val estimation_dialog = dialog.findViewById(R.id.et_achiv_dialog) as EditText
        estimation_dialog.setText(estimation.toString())
        val number_dialog = dialog.findViewById(R.id.et_number_dialog) as EditText
        number_dialog.setText(number.toString())

        val yesBtn = dialog.findViewById(R.id.btn_yes_change) as Button
        val noBtn = dialog.findViewById(R.id.btn_no_change) as Button

        yesBtn.setOnClickListener {
            if((name.equals(name_dialog.text)) && (stars == (stars_dialog.selectedItemPosition)+1)
                && (estimation == (estimation_dialog.text.toString()).toDouble())
                && (number == (number_dialog.text.toString()).toInt())) {
                Toast.makeText(this, "Данные не были изменены", Toast.LENGTH_SHORT).show()
            } else {

                val nam_d: String = name_dialog.text.toString()
                val estimation_d: Double = (estimation_dialog.text.toString()).toDouble()
                val number_d: Int = (number_dialog.text.toString()).toInt()

                val Hl = HostelModel(name = nam_d, stars = stars_dialog.selectedItemPosition + 1, estimation = estimation_d, number = number_d)
                var id: Int = sqLiteHelper.getIdHostel(name)[0]
                val status = sqLiteHelper.updateHostel(Hl, id)
                if (status > -1) {
                    Toast.makeText(this, "Данные успешно изменены!", Toast.LENGTH_SHORT).show()
                    getHostels()
                } else {
                    Toast.makeText(this,"Данные не были изменены!", Toast.LENGTH_SHORT).show()
                }
            }
            dialog.dismiss()
        }
        noBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    //Функция очистки формы
    private fun clearForms() {
        etName.setText("")
        spStars.setSelection(0)
        etAchiv.setText("")
        etNumber.setText("")
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = HostelAdapter()
        recyclerView.adapter = adapter
    }


    //Функция инициализации
    private fun initView(){
        etName = findViewById(R.id.et_name)
        spStars = findViewById(R.id.sp_star)
        etAchiv = findViewById(R.id.et_achiv)
        etNumber = findViewById(R.id.et_number)
        btnAdd = findViewById(R.id.btn_add)
        btnView = findViewById(R.id.btn_view)
        btnZapros1 = findViewById(R.id.btn_zapros1)
        btnZapros2 = findViewById(R.id.btn_zapros2)
        btnZapros3 = findViewById(R.id.btn_zapros3)
        btnZapros4 = findViewById(R.id.btn_zapros4)
        recyclerView = findViewById(R.id.recyclerViewHostel)
        etFind = findViewById(R.id.et_find)
    }
}