package com.it_kot_karapetyan_irina.accountthelper

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Api.Client
import com.google.firebase.auth.*
import com.it_kot_karapetyan_irina.MainActivity
import com.it_kot_karapetyan_irina.R
import com.it_kot_karapetyan_irina.constants.FirebaseConstants

class AccountHelper(act: MainActivity) {
    private val act = act
    private lateinit var Client_: GoogleSignInClient

    //получаем клиента для входа в систему
    private fun getSignInClient(): GoogleSignInClient {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(act.getString(R.string.default_web_client_id)).requestEmail().build()
        return GoogleSignIn.getClient(act, gso)
    }

    /////////
    public fun vxod_Google() {
        Client_ = getSignInClient()
        //Получаем интент для входа
        val intent = Client_.signInIntent
//запускаем интент
        act.startActivityForResult(intent, 1)
    }
    // создаем функцию выхода из гугла аакаунта

    public fun OutGoogle() {
        getSignInClient().signOut()// выход

    }

    ///////
    fun signInFirebaseGoogle(token: String) {
        // создадит константу, превратим гаш токкен в креденшел (учетные данные)
        val credential = GoogleAuthProvider.getCredential(token, null)
        //выполняем регистрацию
        act.mAut.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                act.Vxod(task.result?.user)
                Toast.makeText(act, "Вход выполнен", Toast.LENGTH_LONG).show()

            }
        }

    }


    // получаем емайл и
    @SuppressLint("SuspiciousIndentation")
    fun registration(email: String, password: String)//передаем сюда данные с едит текста
    {
        //функция должна сработать только если эти данные не пустые
        if (email.isNotEmpty() && password.isNotEmpty())
        {
            //Создайте пользователя с помощью электронной почты и пароля
            // и добавить задачу, которая вызывается при завершении действия - addOnCompleteListener
            act.mAut.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {  // eсли успешно зарегистрировались
                    emailProver(task.result?.user!!)// вызываем функцию которая будет отправлять письмо
                    // вызываем функцию для обновления текста почты пользователея на навегейшен вью
                    act.Vxod(task.result?.user)
                }
                else //если не получилось зарегистрироваться
                {
                    Toast.makeText(
                        act, act.resources.getString(R.string.неудачно_совсем),
                        Toast.LENGTH_LONG
                    ).show()
                  Log.d("DDDD", "Ошибка" + task.exception) // ввыведим в лог ошибку

                    if (task.exception is FirebaseAuthInvalidCredentialsException)
                    {
                        var exception = task.exception as FirebaseAuthInvalidCredentialsException
                      //  Log.d("DDDD", "Ошибка_код + ${exception.errorCode}")
                        if (exception.errorCode== FirebaseConstants.ERROR_INVALID_EMAIL)
                        {
                            Toast.makeText(
                                act, "Адрес электронной почты неправильно отформатирован ",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    if (task.exception is FirebaseAuthUserCollisionException)
                    {
                        var exception = task.exception as FirebaseAuthUserCollisionException
                          Log.d("DDDD", "Ошибка_код + ${exception.errorCode}")
                      if (exception.errorCode== FirebaseConstants.ERROR_EMAIL_ALREADY_IN_USE)
                        {
                            LankEmailTog (email, password)

                            Toast.makeText(
                                act, "Вы успешно подсоеденили Email адрес к Google аккаунту ",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    if (task.exception is FirebaseAuthWeakPasswordException)
                    {
                        var exception = task.exception as FirebaseAuthWeakPasswordException
                        Log.d("DDDD", "Ошибка_код + ${exception.errorCode}")
                      if (exception.errorCode== FirebaseConstants.ERROR_WEAK_PASSWORD)
                        {

                            Toast.makeText(
                                act, "Указанный пароль недействителен. [ Пароль должен содержать не менее 6 символов ] ",
                                Toast.LENGTH_LONG
                            ).show()

                        }
                    }
                }
            }
        }
    }

    //  программирование регистрации
    fun vxod_vxod(email: String, password: String)//передаем сюда данные с едит текста
    { //функция должна сработать только если эти данные не пустые
        if (email.isNotEmpty() && password.isNotEmpty()) {
            //вызываем функцию входа
            // и добавить задачу, которая вызывается при завершении действия - addOnCompleteListener
            act.mAut.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {  // eсли успешно зарегистрировались
                    // вызываем функцию для обновления текста почты пользователея на навегейшен вью
                    act.Vxod(task.result?.user)
                } else //если не получилось зарегистрироваться
                {
                    Log.d("DDDD", "Ошибкаrrrrr" + task.exception) // ввыведим в лог ошибку
                    Toast.makeText(
                        act, act.resources.getString(R.string.не_удалось_войти),
                        Toast.LENGTH_LONG
                    ).show()
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        var exception = task.exception as FirebaseAuthInvalidCredentialsException

                        if (exception.errorCode == FirebaseConstants.ERROR_INVALID_EMAIL) {
                            Toast.makeText(
                                act, "неверный формат почты",
                                Toast.LENGTH_LONG
                            ).show()

                        } else if (exception.errorCode == FirebaseConstants.ERROR_WRONG_PASSWORD) {
                            Toast.makeText(
                                act, "Неверный адрес или пароль ",
                                Toast.LENGTH_LONG
                            ).show()

                        }
                    }
                    // Если был введен незарегистрированный email
                    else if (task.exception is FirebaseAuthInvalidUserException) {
                        var exception = task.exception as FirebaseAuthInvalidUserException
                        Log.d("DDDD", "Ошибка + ${exception.errorCode}")

                        if (exception.errorCode == FirebaseConstants.ERROR_USER_NOT_FOUND) {
                            Toast.makeText(
                                act,
                                "Нет записи пользователя, соответствующей этому идентификатору. Возможно, пользователь был удален. ",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    //   Log.d("DDDD","Exception:" + task.exception) // ввыведим в лог ошибку
                    //task.exception- Возвращает исключение, вызвавшее сбой задачи.
                }
            }
        }
    }
    // создаем функцию для отправки подверждающего письма,
    // чтобы отправлялось письмо на почту ссылко на потверждение
    private fun emailProver(user: FirebaseUser) {
        user.sendEmailVerification().addOnCompleteListener { task ->
            if (task.isSuccessful) {  // eсли успешно зарегистрировались, если задача отмененина
                Toast.makeText(act, act.resources.getString(R.string.удачно), Toast.LENGTH_LONG)
                    .show()
            } else //если не получилось зарегистрироваться
            {
                Toast.makeText(act, act.resources.getString(R.string.неудачно), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }




    // Создаем функцию для объеденения почты и гугл аккаунта

    private  fun LankEmailTog (email: String, password: String)
    { // создаем переменную учетных данных
       // var credential= EmailAuthProvider.getCredential(email,password)
        val credential= EmailAuthProvider.getCredential(email, password)
        // отправляем текущему пользователю ссылку с текущими данными
        act.mAut.currentUser?.linkWithCredential(credential)?.addOnCompleteListener { task->
            if (task.isSuccessful)
            {
                Toast.makeText(
                    act, "Email успешно подключен к Google аккаунту",
                    Toast.LENGTH_LONG
                ).show()
            }
            else {
                Toast.makeText(
                    act, "У вас уже есть аккаунт с этим Email адресом войдите по Google аккаунту и подключите почту",
                    Toast.LENGTH_LONG
                ).show()
            }

        }

    }


}
