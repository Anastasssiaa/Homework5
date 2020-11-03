package com.example.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Handler;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class SocnetAdapter extends RecyclerView.Adapter<SocnetAdapter.ViewHolder> {

    private static final String TAG = "WEATHER";
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?lat=55.75&lon=37.62&appid=";


    private SocialDataSource  dataSource;
    private OnItemClickListener itemClickListener;  // Слушатель будет устанавливаться извне

    // Передаем в конструктор источник данных
    // В нашем случае это массив, но может быть и запросом к БД
    public SocnetAdapter(SocialDataSource  dataSource){
        this.dataSource = dataSource;
    }

    // Создать новый элемент пользовательского интерфейса
    // Запускается менеджером
    @NonNull
    @Override
    public SocnetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Создаем новый элемент пользовательского интерфейса
        // Через Inflater
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);
        // Здесь можно установить всякие параметры
        ViewHolder vh = new ViewHolder(v);
        if (itemClickListener != null) {
            vh.setOnClickListener(itemClickListener);
        }
        return vh;
    }

    // Заменить данные в пользовательском интерфейсе
    // Вызывается менеджером
    @Override
    public void onBindViewHolder(@NonNull SocnetAdapter.ViewHolder viewHolder, int i) {
        // Получить элемент из источника данных (БД, интернет...)
        // Вынести на экран используя ViewHolder
        Soc soc = dataSource.getSoc(i);
        viewHolder.setData(soc.getCity(), soc.getDegrees(), soc.getDegreesForWeek());
    }

    // Вернуть размер данных, вызывается менеджером
    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    // Интерфейс для обработки нажатий как в ListView
    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    // Сеттер слушателя нажатий
    public void SetOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    // Этот класс хранит связь между данными и элементами View
    // Сложные данные могут потребовать несколько View на
    // один пункт списка
    public class ViewHolder extends RecyclerView.ViewHolder {
        private EditText cities;
        private EditText degrees;
        //private TextView degreesForWeek;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cities = itemView.findViewById(R.id.cities);
            degrees = itemView.findViewById(R.id.degrees);
           // degreesForWeek = itemView.findViewById(R.id.degreesForWeek);
        }

        public void setOnClickListener(final OnItemClickListener listener){
            degrees.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Получаем позицию адаптера
                    int adapterPosition = getAdapterPosition();
                    // Проверяем ее на корректность
                    if (adapterPosition == RecyclerView.NO_POSITION) return;
                    listener.onItemClick(v, adapterPosition);

                    final URL uri = new URL(WEATHER_URL + BuildConfig.WEATHER_API_KEY);
                    final Handler handler = new Handler(); // Запоминаем основной поток
                    new Thread(new Runnable() {
                        public void run() {
                            HttpsURLConnection urlConnection = null;
                            try {
                                urlConnection = (HttpsURLConnection) uri.openConnection();
                                urlConnection.setRequestMethod("GET"); // установка метода получения данных -GET
                                urlConnection.setReadTimeout(10000); // установка таймаута - 10 000 миллисекунд
                                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream())); // читаем  данные в поток
                                String result = getLines(in);
                                // преобразование данных запроса в модель
                                Gson gson = new Gson();
                                final Parcel weatherRequest = gson.fromJson(result, Parcel.class);
                                // Возвращаемся к основному потоку
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        displayWeather(weatherRequest);
                                    }
                                });
                            } catch (Exception e) {
                                Log.e(TAG, "Fail connection", e);
                                e.printStackTrace();
                            } finally {
                                if (null != urlConnection) {
                                    urlConnection.disconnect();
                                }
                            }
                        }
                    }).start();
                } catch (MalformedURLException e) {
                    Log.e(TAG, "Fail URI", e);
                    e.printStackTrace();
                }





            });
        }

        private String getLines(BufferedReader in) {
            return in.lines().collect(Collectors.joining("\n"));
        }

        private void displayWeather(Parcel weatherRequest){
            cities.setText(weatherRequest.getCityName());
            degrees.setText(String.format("%f2", weatherRequest.getDegrees()));
        }
    };


    public void setData(String cities, int degrees, int degreesForWeek){
            //getDegreesForWeek().setText(degreesForWeek);
            getDegrees().setText(degrees);
            getCities().setText(cities);
        }

        /*public TextView getDegreesForWeek() {
            return degreesForWeek;
        }*/
        public TextView getCities() {
            return cities;
        }
        public TextView getDegrees() {
            return degrees;
        }
    }

}
