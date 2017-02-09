package com.example.martijn.myapplication.src;

import API.api.*;
/**
 * Created by Quinten on 16-11-2016.
 * Dit is de template die word gebruikt voor elke callback die gebruikt word in de app
 */

public interface OnTaskCompleted<K> {
    void onTaskCompleted(K response);
}
