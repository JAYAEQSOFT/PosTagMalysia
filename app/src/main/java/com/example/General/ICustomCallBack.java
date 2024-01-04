package com.example.General;

import com.example.Model.Counter;

import java.util.List;

public interface ICustomCallBack{




     //   <T> void onSucess(List<T> value);
        <T> void onSucess(T value);
        void onFailure(String e);

}
