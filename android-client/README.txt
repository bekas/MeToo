КАК ПОЛУЧИТЬ DEBUG-СЕРТИФИКАТ ДЛЯ GOOGLE.MAPS

1) Из папки JDK запустить в консоли:
keytool -v -list -alias androiddebugkey -keystore "C:\Users\ИМЯ_ПОЛЬЗОВАТЕЛЯ\.android\debug.keystore" -storepass android -keypass android
2) Получить ключ по слепку MD5 можно по адресу:
https://developers.google.com/android/maps-api-signup?hl=ru
3) В файле разметки интерфейса внутри описания компонента com.google.android.maps.MapView добавить строчку:
android:apiKey="СГЕНЕРИРОВАННЫЙ НА САЙТЕ КЛЮЧ"