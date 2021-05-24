package pl.edu.ug.cwiczpamiec;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //uchwyty do obrazków, współdzielone
    ImageView red;
    ImageView yellow;
    ImageView green;

    ImageView red_light;
    ImageView yellow_light;
    ImageView green_light;

    int[] states = new int[1000];

    boolean gameIsActive = false;

    int ile = 0;
    int licznik_klikniec = 0;

    TextView zapamietaj;

    int licznik_zero = 0;
    int licznik_jeden = 0;
    int licznik_dwa = 0;

    public void dzwiek(int nazwa) {
        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), nazwa);
        mediaPlayer.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        red = findViewById(R.id.red);
        yellow = findViewById(R.id.yellow);
        green = findViewById(R.id.green);

        red_light = findViewById(R.id.red_light);
        yellow_light = findViewById(R.id.yellow_light);
        green_light = findViewById(R.id.green_light);

        //losujemy liczby od 0 do 2 i wrzucamy do tablicy
        //0 - czerwony, 1 - żółty, 2 - zielony

        Random r = new Random();

        for(int i=0; i<1000; i++) {
            states[i] = r.nextInt(3);
            Log.i("sprawdzam", "Numer " + String.valueOf(i) + " " + String.valueOf(states[i]));
        }
    }

    public void game(View view) {
        if(!gameIsActive) {
            gameIsActive = true;
            dzwiek(R.raw.start);
            sequence(); //wywolanie metody odgrywajacej sekwencje
        }
    }

    public void sequence() {
        //wyłączamy użytkownikowi możliwość klikania światełek w trakcie odgrywania sekwencji do zapamiętania
        red_light.setEnabled(false);
        yellow_light.setEnabled(false);
        green_light.setEnabled(false);

        zapamietaj = findViewById(R.id.zapamietajTextView);

        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                zapamietaj.setText("Następna runda za: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                zapamietaj.setText("Zapamietaj sekwencję");

                ile++;
                for(int i = 0; i < ile; i++) {

                    switch (states[i]) {
                        case 0: {
                            licznik_zero++;

                            new CountDownTimer((licznik_zero + licznik_jeden + licznik_dwa) * 3000, 1000) {
                                public void onFinish() {
                                    // When timer is finished
                                    // Execute your code here
                                    red.setAlpha(1.0f); //zapalamy światełko
                                    dzwiek(R.raw.beep1);
                                }

                                public void onTick(long millisUntilFinished) {
                                    // millisUntilFinished    The amount of time until finished.
                                }
                            }.start();

                            new CountDownTimer((licznik_zero + licznik_jeden + licznik_dwa) * 4000, 1000) {
                                public void onFinish() {
                                    // When timer is finished
                                    // Execute your code here
                                    red.setAlpha(0.0f); //gasimy światełko
                                }

                                public void onTick(long millisUntilFinished) {
                                    // millisUntilFinished    The amount of time until finished.
                                }
                            }.start();

                        } break;
                        case 1: {
                            licznik_jeden++;

                            new CountDownTimer((licznik_jeden + licznik_zero + licznik_dwa) * 3000, 1000) {
                                public void onFinish() {
                                    // When timer is finished
                                    // Execute your code here
                                    yellow.setAlpha(1.0f); //zapalamy światełko
                                    dzwiek(R.raw.beep2);
                                }

                                public void onTick(long millisUntilFinished) {
                                    // millisUntilFinished    The amount of time until finished.
                                }
                            }.start();

                            new CountDownTimer((licznik_jeden + licznik_zero + licznik_dwa) * 4000, 1000) {
                                public void onFinish() {
                                    // When timer is finished
                                    // Execute your code here
                                    yellow.setAlpha(0.0f); //gasimy światełko
                                }

                                public void onTick(long millisUntilFinished) {
                                    // millisUntilFinished    The amount of time until finished.
                                }
                            }.start();

                        } break;
                        case 2: {
                            licznik_dwa++;

                            new CountDownTimer((licznik_dwa + licznik_zero + licznik_jeden) * 3000, 1000) {
                                public void onFinish() {
                                    // When timer is finished
                                    // Execute your code here
                                    green.setAlpha(1.0f); //zapalamy światełko
                                    dzwiek(R.raw.beep3);
                                }

                                public void onTick(long millisUntilFinished) {
                                    // millisUntilFinished    The amount of time until finished.
                                }
                            }.start();

                            new CountDownTimer((licznik_dwa + licznik_zero + licznik_jeden) * 4000, 1000) {
                                public void onFinish() {
                                    // When timer is finished
                                    // Execute your code here
                                    green.setAlpha(0.0f); //gasimy światełko
                                }

                                public void onTick(long millisUntilFinished) {
                                    // millisUntilFinished    The amount of time until finished.
                                }
                            }.start();

                        } break;
                    } //switch

                } //for
                licznik_zero=0;
                licznik_jeden=0;
                licznik_dwa=0;

                new CountDownTimer(ile*4000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        //code
                    }

                    public void onFinish() {
                        zapamietaj.setText("Powtórz sekwencję");
                        active(); //włączamy możliwość klikania światełek po odegraniu sekwencji; wcześniej odbieramy użytkownikowi możliwosć klikania
                    }
                }.start();

            }
        }.start();
        licznik_klikniec = 0;

    } //sequence()

    public void redLightOnClick(View view) {
        if(gameIsActive) {
            licznik_klikniec++;

            red.setAlpha(1.0f); //zapalamy światełko
            dzwiek(R.raw.beep1);
            new CountDownTimer(500, 500) {
                public void onFinish() {
                    // When timer is finished
                    // Execute your code here
                    red.setAlpha(0.0f); //gasimy światełko
                }

                public void onTick(long millisUntilFinished) {
                    // millisUntilFinished    The amount of time until finished.
                }
            }.start();


            if(states[licznik_klikniec-1] != 0) {
                ile--;
                zapamietaj.setText("Skucha! Twój wynik: " + ile);
                dzwiek(R.raw.fail2);
                gameIsActive = false;
            }
            if(licznik_klikniec == ile) {
                sequence();
            }
        }
    }

    public void yellowLightOnClick(View view) {
        if(gameIsActive) {
            licznik_klikniec++;

            yellow.setAlpha(1.0f); //zapalamy światełko
            dzwiek(R.raw.beep2);
            new CountDownTimer(500, 500) {
                public void onFinish() {
                    // When timer is finished
                    // Execute your code here
                    yellow.setAlpha(0.0f); //gasimy światełko
                }

                public void onTick(long millisUntilFinished) {
                    // millisUntilFinished    The amount of time until finished.
                }
            }.start();


            if(states[licznik_klikniec-1] != 1) {
                ile--;
                zapamietaj.setText("Skucha! Twój wynik: " + ile);
                dzwiek(R.raw.fail2);
                gameIsActive = false;
            }
            if(licznik_klikniec == ile) {
                sequence();
            }
        }
    }

    public void greenLightOnClick(View view) {
        if(gameIsActive) {
            licznik_klikniec++;

            green.setAlpha(1.0f); //zapalamy światełko
            dzwiek(R.raw.beep3);
            new CountDownTimer(500, 500) {
                public void onFinish() {
                    // When timer is finished
                    // Execute your code here
                    green.setAlpha(0.0f); //gasimy światełko
                }

                public void onTick(long millisUntilFinished) {
                    // millisUntilFinished    The amount of time until finished.
                }
            }.start();

            if(states[licznik_klikniec-1] != 2) {
                ile--;
                zapamietaj.setText("Skucha! Twój wynik: " + ile);
                dzwiek(R.raw.fail2);
                gameIsActive = false;
            }
            if(licznik_klikniec == ile) {
                sequence();
            }
        }
    }

    public void active() {
        red_light.setEnabled(true);
        yellow_light.setEnabled(true);
        green_light.setEnabled(true);
    }
}