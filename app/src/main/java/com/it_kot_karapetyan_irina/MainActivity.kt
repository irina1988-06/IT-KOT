package com.it_kot_karapetyan_irina

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.material.navigation.NavigationView
import com.google.firebase.FirebaseError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.it_kot_karapetyan_irina.act.Edit_ads_Ac
import com.it_kot_karapetyan_irina.dialogs.DialogConst.SING_IN
import com.it_kot_karapetyan_irina.dialogs.DialogConst.SING_UP
import com.it_kot_karapetyan_irina.dialogs.DialogHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_content.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var Email_pol: TextView // переменная для почты пользователя, отбражается в обложке
    private val dialogHelper = DialogHelper(this)// ссылка на диолог
    val mAut =
        FirebaseAuth.getInstance()// ссылка на регистрацию в фаер басс, получаем доступ к регистрации
    val mAuth = Firebase.auth

    //  val mAut= Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }


    // добавляем функцию для прослушивания нажатия кнопок в меню

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
// Создаем условие если кнопка в меню нажата
        if (item.itemId==R.id.new_ad)
        {
            var i= Intent(this,Edit_ads_Ac::class.java)
            startActivity(i)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            //Log.d("D","Все норм")

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            // попробуем запустить акаунт
            try {
                //ApiException::class.java- для выявления оштбок
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    dialogHelper.accHelper.signInFirebaseGoogle((account.idToken!!))
                }

            } catch (e: ApiException) {   //Exception- исклюсение, если не получилось
                Log.d("D", "Ошибка: ${e.message}")// выводим ошибки
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun init() {


        setSupportActionBar(toolbar)
        // создаем кнопку
        var button_Start =
            ActionBarDrawerToggle(this, Draw_menu, toolbar, R.string.app_Open, R.string.app_close)
        //указываем какое меню будет открываться по кнопке
        Draw_menu.addDrawerListener(button_Start)
        // синхронизируем кнопку
        button_Start.syncState()
        // необходимо указать от куда будем получать событие. NavigationView- id с главного активити
        NavigationView.setNavigationItemSelectedListener(this)
        // инициализируем переменную где будет отбражаться емайл   нашего пользователя
        Email_pol = NavigationView.getHeaderView(0).findViewById(R.id.pochta)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.Программирование -> {

                textView.text = "Программирование"
            }


            R.id.Программирование_андроид -> {

                textView.text = "Программирование_андроид"
            }
            R.id.D3 -> {

                textView.text = "3d"
            }

            R.id.Робототехника -> {

                textView.text = "Робототехника"
            }

            R.id.программирование -> {

                textView.text = "программирование"
            }
            R.id.Инженерное_дело -> {

                textView.text = "Инженерное_дело"
            }
            R.id.Педагогика -> {

                textView.text = "Педагогика"
            }
            R.id.app_sing_up -> {

                //textView.text="app_sing_up"
                dialogHelper.createSignDialog(SING_UP)
            }
            R.id.app_sing_in -> { // для входа

                dialogHelper.createSignDialog(SING_IN)
            }
            R.id.app_sing_out -> { // для выхода

                Vxod(null)
                mAut.signOut()// выход
                dialogHelper.accHelper.OutGoogle()
            }


        }
        // чтобы меню закрывалось
        Draw_menu.closeDrawer(GravityCompat.START)
        return true
    }

    // функция для входа. user: FirebaseUser-получаем пользователя, так как нам нужно получить его почту
    fun Vxod(user: FirebaseUser?)//?- означает, что данные могут быть ноль, то есть пусто
    {
        Email_pol.text = if (user == null) {
            resources.getString(R.string.Вход_или_регистрация)
        } else {
            user.email
        }

    }

    override fun onStart() {
        super.onStart()
        Vxod(mAut.currentUser) //mAut- функция регистрации, откуда мы берем пользователя,
        // если пользова нет то передаст null

    }
}