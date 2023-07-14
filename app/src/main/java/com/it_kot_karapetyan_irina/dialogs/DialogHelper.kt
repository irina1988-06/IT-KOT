package com.it_kot_karapetyan_irina.dialogs

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.View
import android.widget.Toast
import com.it_kot_karapetyan_irina.MainActivity
import com.it_kot_karapetyan_irina.R

import com.it_kot_karapetyan_irina.accountthelper.AccountHelper
import com.it_kot_karapetyan_irina.databinding.DialogBinding


class DialogHelper (act:MainActivity) {

    private  val act=act
      val accHelper= AccountHelper (act)// создаем объект класса
    @SuppressLint("SuspiciousIndentation")
    fun  createSignDialog(Index:Int)
    {
        val builder=AlertDialog.Builder(act)// создаем окно для диалога
       val rootDialogElement=DialogBinding.inflate(act.layoutInflater)//
        val  dialog = builder.create()
          vibor(Index,rootDialogElement)
        //Создаем слушатель нажатий кнопки
        rootDialogElement.btSingUpIn.setOnClickListener {
           click(dialog,Index,rootDialogElement)

       }
        rootDialogElement.btForgetP.setOnClickListener {
            clickResetPassword(dialog,rootDialogElement)
        }

        rootDialogElement.BTGoogleSignIn.setOnClickListener {
            accHelper.vxod_Google()
            dialog?. dismiss() // скрываем окно регестрации

        }


        dialog.setView(rootDialogElement.root)// в диалоге показываем наше окно
        dialog.show()// показываем окно регистрации

    }

    private fun clickResetPassword(dialog: AlertDialog?, rootDialogElement: DialogBinding) {

        if (rootDialogElement.edSignEmail.text.isNotEmpty())
        {
            // отправляем письмо для восстановления
            act.mAut.sendPasswordResetEmail(rootDialogElement.edSignEmail.text.toString()).addOnCompleteListener { task->
                if (task.isSuccessful){  // eсли успешно зарегистрировались, если задача отмененина
                    Toast.makeText(act,act.resources.getString(R.string.Восстановление), Toast.LENGTH_LONG).show()
                    dialog?. dismiss() // скрываем окно регестрации

                }else{
                    rootDialogElement.textResetPassword.visibility=View.VISIBLE
                }

            }
        }

        else
        {
            rootDialogElement.textResetPassword.visibility=View.VISIBLE
        }



    }

    private fun click(dialog: AlertDialog?, Index: Int, rootDialogElement: DialogBinding) {
        dialog?. dismiss() // скрываем окно регестрации
        if (Index==DialogConst.SING_UP)
        {
            accHelper.registration(rootDialogElement.edSignEmail.getText().toString().trim(),
                rootDialogElement.edSignPassword.getText().toString().trim())
        }

        else
        {
            accHelper.vxod_vxod(rootDialogElement.edSignEmail.getText().toString().trim(),
                rootDialogElement.edSignPassword.getText().toString().trim())
        }
    }

    private fun vibor(Index: Int, rootDialogElement: DialogBinding) {
        if (Index==DialogConst.SING_UP)
        {
            rootDialogElement.y.text="Регистрация"
            rootDialogElement.btSingUpIn.text= "Зарегистрироваться"
        }
        else
        {
            rootDialogElement.y.text="Вход"
            rootDialogElement.btSingUpIn.text= "Войти"
            rootDialogElement.btForgetP.visibility= View.VISIBLE
        }
    }

}