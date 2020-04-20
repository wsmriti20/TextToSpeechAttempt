package com.example.texttospeechattempt02;

interface TTSListener {
    public void speak(String text);
    public void pause(long duration);
}
