/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

/**
 *
 * @author silaban
 */
import io.socket.SocketIO;

import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

public class CTeller implements Runnable {

    private SocketIO socket;
    private String ipadd = "http://127.0.0.1:8087";
    private final ChatCallback callback;


    public CTeller(ChatCallbackAdapter callback,String ipadd) {        
        this.callback = new ChatCallback(callback);   
         this.ipadd = ipadd;
    }

    @Override
    public void run() {
        try {
            socket = new SocketIO(ipadd, callback);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            JSONObject json = new JSONObject();
            json.putOpt("message", message);
            socket.emit("user message", json);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    public void sendQueue(String message) {
        socket.emit("q_satu", message);
    }
    
    
       public void sendMulai(String pos, String noloket,String loketname) {
        try {
            JSONObject json = new JSONObject();
            json.putOpt("server", "99");
            json.putOpt("pos", pos);
            json.putOpt("noloket", noloket);
            json.putOpt("loket", loketname);           
            socket.emit("mulai", json);
        } catch (JSONException ex) {
        }
    }
       
       public void sendStop(String pos, String noloket,String loketname) {
        try {
            JSONObject json = new JSONObject();
            json.putOpt("server", "99");
            json.putOpt("pos", pos);
            json.putOpt("noloket", noloket);
            json.putOpt("loket", loketname);           
            socket.emit("stop", json);
        } catch (JSONException ex) {
        }
    }
    
      public void sendLewat(String pos,String noloket, String loketname) {
        try {
            JSONObject json = new JSONObject();
            // json.putOpt("message", message);
            json.putOpt("server", "99");
            json.putOpt("antrian", "1");
            json.putOpt("pos", pos);
            json.putOpt("noloket", noloket);
            json.putOpt("loket", loketname);
            socket.emit("lewat", json);
        } catch (JSONException ex) {
        }
    }

    public void sendPanggil(String pos,String noloket,String loket) {
        try {
            JSONObject json = new JSONObject();
            json.putOpt("server", "99");
            json.putOpt("antrian", "1");
            json.putOpt("pos", pos);
            json.putOpt("noloket", noloket);
            json.putOpt("loket", loket);
            socket.emit("panggil", json);
        } catch (JSONException ex) {
        }
    }
    
     public void sendPanggilClient(String id,String message) {
        try {
            JSONObject json = new JSONObject();
            // json.putOpt("message", message);
            json.putOpt("message", message);
            json.putOpt("id", id);
            socket.emit("panggilClient", json);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
     
     
     
     
        public void sendBroadcastPanggil(String message,String pos, String count) {
        try {
            JSONObject json = new JSONObject();
            json.putOpt("antrian", message);
            json.putOpt("pos", pos);
            json.putOpt("count", count);
            socket.emit("broadcast_end_panggil", json);
        } catch (JSONException ex) {
        }
    }
        
        
      public void sendLayan(String pos,String noloket,String loket) {
          try {
            JSONObject json = new JSONObject();
            json.putOpt("server", "99");
            json.putOpt("pos", pos);
            json.putOpt("noloket", noloket);
            json.putOpt("loket", loket);
            socket.emit("layan", json);
        } catch (JSONException ex) {
        }
    }
      
         public void sendSiap(String pos,String noloket,String loket) {
          try {
            JSONObject json = new JSONObject();
            json.putOpt("server", "99");
            json.putOpt("pos", pos);
            json.putOpt("noloket", noloket);
            json.putOpt("loket", loket);
            socket.emit("siap", json);
        } catch (JSONException ex) {
        }
    }

         public void sendcheck(String id) {
        try {
            JSONObject json = new JSONObject();
            json.putOpt("id", id);
            socket.emit("checkin", json);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        }
    public void join(String nickname) {
        try {
            JSONObject json = new JSONObject();
            json.putOpt("nickname", nickname);
            socket.emit("nickname", callback, json);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
