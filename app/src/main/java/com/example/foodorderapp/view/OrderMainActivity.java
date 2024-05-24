package com.example.foodorderapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.example.foodorderapp.R;
import com.example.foodorderapp.UserManager;
import com.example.foodorderapp.object.DetailFoodDTO;
import com.example.foodorderapp.retrofit.ApiService;
import com.example.foodorderapp.retrofit.DetailFoodResponsive;
import com.example.foodorderapp.viewmodal.PostOrderViewModel;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderMainActivity extends BaseActivity {
    private TextInputLayout outlinedTextField;
    ImageView food_img_order;
    PostOrderViewModel postOrderViewModel;
    private TextView quantityOrder, priceFoodOrder, totalPriceOrder, nameOrder, ingredientOrder, priceOrder, shipping_fee, btnPay;
    TextView errorQuantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        int foodId = getIntent().getIntExtra("foodId", -1);
        if (foodId != -1) {
            getFetailFood(foodId);
        }
    }




    private void getFetailFood(int foodId) {
        // Gọi API để nhận dữ liệu chi tiết của món ăn
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<DetailFoodResponsive> call = apiService.getDetailFood(foodId);
        call.enqueue(new Callback<DetailFoodResponsive>() {
            @Override
            public void onResponse(Call<DetailFoodResponsive> call, Response<DetailFoodResponsive> response) {
                if (response.isSuccessful()) {
                    DetailFoodResponsive detailFoodResponse = response.body();
                    detailFoodResponse.setSuccess(true);
                    if (detailFoodResponse != null && detailFoodResponse.isSuccess()) {
                        DetailFoodDTO detailFoodDTO = detailFoodResponse.getData();
                        // Xử lý dữ liệu và cập nhật giao diện
                        updateUI(detailFoodDTO);
                    } else {
                        Log.e("OrderMainActivity", "API call failed: Invalid response.");
                    }
                } else {
                    Log.e("OrderMainActivity", "API call failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<DetailFoodResponsive> call, Throwable t) {
                Log.e("OrderMainActivity", "API call failed: " + t.getMessage());
            }
        });
    }
    private void updateUI(DetailFoodDTO detailFoodDTO) {
        postOrderViewModel = new PostOrderViewModel();
        setContentView(R.layout.activity_order_main); // Gọi setContentView() sau khi dữ liệu đã được xử lý
        // Ánh xạ các view từ layout
        nameOrder = findViewById(R.id.nameOrder);
        food_img_order = findViewById(R.id.food_img_order);
        priceFoodOrder = findViewById(R.id.priceFoodOrder);
        ingredientOrder = findViewById(R.id.ingredientOrder);
        outlinedTextField = findViewById(R.id.outlinedTextField);
        quantityOrder = findViewById(R.id.quantityOrder);
        totalPriceOrder = findViewById(R.id.totalPriceOrder);
        priceOrder = findViewById(R.id.priceOrder);
        shipping_fee = findViewById(R.id.shipping_fee);
        btnPay = findViewById(R.id.btnPayment);
        errorQuantity = findViewById(R.id.errorQuantity);

        // Set dữ liệu cho các view
        Picasso.get().load(detailFoodDTO.getImg_thumbnail()).into(food_img_order);
        nameOrder.setText(detailFoodDTO.getName());
        ingredientOrder.setText(detailFoodDTO.getIngredients());
        priceOrder.setText(String.valueOf(detailFoodDTO.getPrice()));
        priceFoodOrder.setText(String.valueOf(detailFoodDTO.getPrice()));
        double total = Double.parseDouble(priceOrder.getText().toString()) + Double.parseDouble(shipping_fee.getText().toString());
        totalPriceOrder.setText(String.valueOf(total));
        // Get input text
        String inputText = outlinedTextField.getEditText().getText().toString();
        outlinedTextField.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateQuantityAndTotalPrice(s.toString());
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int food_id = detailFoodDTO.getId();
                int user_id = UserManager.getInstance().getUserId();
                int quantity = Integer.parseInt(quantityOrder.getText().toString());
                double total_price = Double.parseDouble(totalPriceOrder.getText().toString());
                postOrderViewModel.postOrder(food_id, user_id, quantity, total_price);
            }
        });
        postOrderViewModel.getPostOrderStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean postOrderStatus) {
                if (postOrderStatus) {
                    Toast.makeText(OrderMainActivity.this, "Đặt hàng thành công, vui lòng kiểm tra thông báo để xem chi tiết", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(OrderMainActivity.this, MainActivity.class);
                    intent.putExtra("fragment", "order");
                    startActivity(intent);
                    finish();

                } else {
                    // Đăng nhập không thành công, hiển thị thông báo lỗi
                }
            }
        });
    }
    private void updateQuantityAndTotalPrice(String newQuantityString) {
        // Kiểm tra nếu newQuantityString không rỗng
        if (!newQuantityString.isEmpty()) {
            // Lấy số lượng mới từ newQuantityString
            int newQuantity = Integer.parseInt(newQuantityString);
            if (newQuantity > 0) {
                // Lấy giá sản phẩm từ priceOrder
                double productPrice = Double.parseDouble(priceOrder.getText().toString());

                // Lấy phí vận chuyển từ shipping_fee
                double shippingFee = Double.parseDouble(shipping_fee.getText().toString());

                // Tính toán tổng giá mới dựa trên số lượng mới, giá của sản phẩm và phí vận chuyển
                double totalPrice = (newQuantity * productPrice) + shippingFee;

                // Cập nhật số lượng và tổng giá trên giao diện
                quantityOrder.setText(String.valueOf(newQuantity));
                totalPriceOrder.setText(String.valueOf(totalPrice));
                errorQuantity.setText("");
                btnPay.setEnabled(true);
            } else {
                errorQuantity.setText("Vui lòng nhập số lượng lớn hơn 0!");
                btnPay.setEnabled(false);
            }
        } else {
            errorQuantity.setText("Vui lòng nhập số lượng!");
            btnPay.setEnabled(false);
        }
    }
}