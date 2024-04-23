package com.example.foodorderapp.retrofit;

import com.example.foodorderapp.object.NotiDTO;

import java.util.List;

public class NotiResponsive {
    private boolean status;
    private List<NotiDTO> data;

    public boolean isStatus() {return status;}

    public void setStatus(boolean status) {this.status=status;}

    public List<NotiDTO> getData(){return data;}

    public void setData(List<NotiDTO> data) {this.data=data;}


}
