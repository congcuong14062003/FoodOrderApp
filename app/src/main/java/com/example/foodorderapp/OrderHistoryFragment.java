package com.example.foodorderapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class OrderHistoryFragment extends Fragment implements IOnItemClickListener {

    private ArrayList<ItemOrderHistory> orderHistories = new ArrayList<>();

    public OrderHistoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Thực hiện yêu cầu API để lấy danh sách đơn hàng
        new FetchOrderHistoryTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_order_history, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recycle_view_history);
        ItemOrderHistoryAdapter historyAdapter = new ItemOrderHistoryAdapter(orderHistories, getContext());
        historyAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(historyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return rootView;
    }

    @Override
    public void onItemClick(int position) {
        // Xử lý khi một mục được nhấp vào
        // Chuyển sang trang chi tiết của đơn hàng với dữ liệu tương ứng
        Intent intent = new Intent(getContext(), DetailActivity.class);
        // Chuyển dữ liệu cần thiết (ví dụ: ID của đơn hàng)
//        intent.putExtra("productId", orderHistories.get(position).getId());
        startActivity(intent);
    }

    private class FetchOrderHistoryTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String orderHistoryJsonString = null;

            try {
                URL url = new URL("https://food-app-server-murex.vercel.app/orders/list/1");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder builder = new StringBuilder();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line).append("\n");
                }
                if (builder.length() == 0) {
                    return null;
                }
                orderHistoryJsonString = builder.toString();
            } catch (IOException e) {
                Log.e("OrderHistoryFragment", "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("OrderHistoryFragment", "Error closing stream", e);
                    }
                }
            }
            return orderHistoryJsonString;
        }

        @Override
        protected void onPostExecute(String orderHistoryJsonString) {
            if (orderHistoryJsonString != null) {
                // Xử lý dữ liệu JSON ở đây và cập nhật RecyclerView
                try {
                    JSONObject jsonObject = new JSONObject(orderHistoryJsonString);
                    boolean status = jsonObject.getBoolean("status");
                    if (status) {
                        JSONArray dataArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject dataObject = dataArray.getJSONObject(i);

                            // Lấy các trường dữ liệu từ JSON
                            String name = dataObject.getString("name");
                            String ingredients = dataObject.getString("ingredients");
                            int quantity = dataObject.getInt("quantity");
                            String orderDatetime = dataObject.getString("order_datetime");
                            double price = dataObject.getDouble("price");
                            String imgUrl = dataObject.getString("img_thumbnail");
                            String purchaseDate = dataObject.getString("order_datetime");
                            double totalPrice = dataObject.getDouble("total_price");
                            ItemOrderHistory itemOrderHistory = new ItemOrderHistory(imgUrl, name, ingredients, price, quantity, purchaseDate, totalPrice);
                            orderHistories.add(itemOrderHistory);
                        }

                        // Cập nhật RecyclerView
                        RecyclerView recyclerView = getView().findViewById(R.id.recycle_view_history);
                        recyclerView.getAdapter().notifyDataSetChanged();
                    } else {
                        // Xử lý trường hợp status là false (nếu cần)
                    }
                } catch (JSONException e) {
                    Log.e("OrderHistoryFragment", "Error parsing JSON", e);
                }
            } else {
                // Xử lý trường hợp không có dữ liệu hoặc có lỗi xảy ra
            }
        }

    }
}
