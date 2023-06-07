package com.example.myapplication;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

//@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity {
    //SERVER
    long chout=0;
    ServerSocket serverSocket;
    Thread Thread1 = null;
    TextView tvIP, tvPort;
    TextView tvMessages;
    EditText etMessage;
    Button btnSend;
    int total_processors=0;
    long start1;
    int temp=0;
    int refsig=48;
    public static ArrayList<ClientHandler> ls;
    public static String SERVER_IP = "";
    public static final int SERVER_PORT = 9654;
    HashMap<String,Integer> wordCount=new HashMap<String,Integer>();
    List<String> region=new ArrayList<String>();

   // HashMap<Integer,Integer> numberofprocessors=new HashMap<Integer,Integer>();
    ArrayList<Integer> processors=new ArrayList<Integer>();
    ArrayList<Long> rtttimers=new ArrayList<Long>();
ArrayList<String> seq2=new ArrayList<String>();
ArrayList<Integer> seq1=new ArrayList<Integer>();
    String combinejson="";
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvIP = findViewById(R.id.tvIP);
        tvPort = findViewById(R.id.tvPort);
        tvMessages = findViewById(R.id.tvMessages);
        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);

        try {
            SERVER_IP = getLocalIpAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        ls=new ArrayList<ClientHandler>();
//new
       /* region.add("Asia");
        region.add("Africa");
        //region.add("Europe");*/



        //end
        Thread1 = new Thread(new Thread1());
        Thread1.start();
        btnSend.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           start1 = System.nanoTime();
                                           message = etMessage.getText().toString().trim() + "\n";
                                           //  int bytes=0;
                                           final int[] total = {0};
// change from here for filewindow_size=
                                           int gcd=findGCD(processors);
                                           // window_size=4-hs.size()+1;
                                        /*   for(int py=0;py<ls.size();py++)
                                           {
                                              ls.get(py).num_process=(ls.get(py).num_process/gcd);
                                           }*/



/*

                                           File f = null;
                                           f = new File("/sdcard/temperature.txt");
                                           //new
                                         //  f = new File("/sdcard/temperatureregion.txt");
                                           long k = f.length();

                                           for (ClientHandler ch : ls) {
                                               total_processors += ch.num_process;
                                           }
                                           Log.d("Number of processors", String.valueOf(k));
*/


                                           //here take array size by taking size/hs.size();


                                           int totalbytes = 0;
                                           int j = 0;
                                          /* for (j = 0; j < ls.size() - 1; j++) {
                                               totalbytes += (int) ((k * ls.get(j).num_process) / total_processors);
                                               // ls.get(i).b=new byte[(int)((k*ls.get(i).num_process)/total_processors)];
                                               ls.get(j).filesize = (int) ((k * (ls.get(j).num_process)) / total_processors);



                                           }
                                           //ls.get(i).b=new byte[(int)(k-totalbytes)];
                                           ls.get(j).filesize = (int) (k - totalbytes);*/



                                           //dividing file
                                           // String path = "/sdcard/project.txt";
                                           String s = "";
                                           int y = 0;
                                           int x = 0;


                                           int numParts = hs.size(); // set number of parts
                                           //   int partSize = (int) Math.ceil((double) k / numParts); // calculate part size

                                           int bytesRead;
                                           long currentFileSize = 0;

                                           String line = "";
                                           try {
                                               int i = 0;
                                               // FileInputStream fis = new FileInputStream("/sdcard/temperature.txt");
                                               BufferedReader reader = new BufferedReader(new FileReader("/sdcard/testsig1.txt"));
                                               BufferedReader reader2[] = new BufferedReader[refsig];
                                               for(i=0;i<refsig;i++) {
                                                   reader2[i]=new BufferedReader( new FileReader("/sdcard/seq"+(i+1)+".txt"));

                                               }
                                               //BufferedReader reader2 = new BufferedReader(new FileReader("/sdcard/reference_signal.txt"));
                                               s = "/sdcard/temp10" + x + ".txt";
                                               File outputFile = new File(s);
                                               BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
                                               String tempstr = "";

                                               //dtw
                                               long start2=System.nanoTime();
                                               while((line= reader.readLine())!=null)
                                               {
                                                   for(int c=0;c<ls.size();c++)
                                                   {
                                                       ls.get(c).out.println(line);
                                                       ls.get(c).out.flush();
                                                   }
                                                   temp++;
                                               }
                                               for(int c=0;c<ls.size();c++)
                                               {
                                                   ls.get(c).out.println("end");
                                                   ls.get(c).out.flush();
                                               }
                                               int count=0;
                                               //dtw
                                               int scount=0;
                                               int each_slave_signal=refsig/hs.size();
                                               int temp_slave_signal=0;
                                               for(i=1;i<=refsig;i++)
                                               {

                                               while ((line = reader2[i-1].readLine()) != null) {

                                                   //   byte byteArrray[] = line.getBytes();
                                                   //   ls.get(i).out.writeInt(byteArrray.length);
                                                   //  Log.d("Server", "String " + byteArrray.length + "sent");
//new
                                                 /*  String data[] = line.split(",");
                                                   if (data[0].equals("Europe")) {
                                                       ls.get(0).out.println(data[1] + "," + data[4] + "," + data[5] + "," + data[6] + "," + data[7]);
//Log.d("data",data[1] + "," + data[4] + "," + data[5] + "," + data[6] + "," + data[7]+"\n");
                                                       ls.get(0).out.flush();
                                                   }
                                                   *//*if (data[0].equals("Asia")) {
                                                       ls.get(0).out.println(data[1] + "," + data[4] + "," + data[5] + "," + data[6] + "," + data[7]);

                                                       ls.get(1).out.flush();
                                                   }*//*
                                                   //Thread.sleep(100);
                                               }
                                               for (int ty = 0; ty < ls.size(); ty++) {
                                                   ls.get(ty).out.println(".");
                                                   ls.get(ty).out.flush();
                                               }
                                           }*/


//end
                                                   //old for constant thread from file

                                                  /* if (temp < ls.get(i).num_process) {

                                                       ls.get(i).out.println(line);

                                                       ls.get(i).out.flush();
                                                       temp++;
                                                   }
                                                   else {
                                                       temp=0;
                                                       i = (i + 1) % ls.size();
                                                       ls.get(i).out.println(line);

                                                       ls.get(i).out.flush();
                                                   }*/

                                                   //for dtw
                                                   ls.get(scount).out.println(line);
                                                   ls.get(scount).out.flush();

                                               }
                                                   temp_slave_signal++;
                                                   if(temp_slave_signal==each_slave_signal)
                                                   {
                                                       ls.get(scount).out.println("");
                                                       ls.get(scount).out.flush();
                                                       scount++;
                                                       temp_slave_signal=0;
                                                   }
                                                   else {
                                                       ls.get(scount).out.println("cons");
                                                       ls.get(scount).out.flush();
                                                   }

                                                //end dtw
                                               }
                                               for(i=0;i<ls.size();i++)
                                               {
                                                   ls.get(i).out.println("");
                                                   ls.get(i).out.flush();
                                               }
                                               long end2=System.nanoTime();
                                               Log.d("CTime", (end2-start2)+"");
int total_windows=0;
                                               int si=0;
                                            total_windows=count-temp+1;
int tempw=total_windows/hs.size(),countw=0;

                                              /* for (int ty = 0; ty < ls.size(); ty++) {
                                                   ls.get(ty).out.println("");
                                                   ls.get(ty).out.flush();
                                               }*/
                                              /* for(i=0;i<ls.size();i++)
                                               {

                                                   if(i==ls.size()-1)
                                                   {
                                                       ls.get(i).windows=total_windows-countw;
                                                   }
                                                   else{
                                                   ls.get(i).windows=tempw;
                                                   countw+=tempw;
                                                   }
                                               }*/
                                              /* for(i=0;i<ls.size();i++)
                                               {
                                                   int n=ls.get(i).windows+temp-1;
                                                   ls.get(i).sid=si;
                                                   ls.get(i).chunkseq.addAll(seq2.subList(si, si + n));
                                             si+=ls.get(i).windows;
                                               }*/
                                              /* for(i=0;i<ls.size();i++)
                                               {
                                                   ls.get(i).out.println(ls.get(i).sid);
                                                   ls.get(i).out.flush();
                                               }*/
                                             /*  for(i=0;i<ls.size();i++)
                                               {
                                                   for(int in=0;in<ls.get(i).chunkseq.size();in++)
                                                   {
                                                       ls.get(i).out.println(ls.get(i).chunkseq.get(in));
                                                       ls.get(i).out.flush();
                                                   }
                                                   ls.get(i).out.println("");
                                                   ls.get(i).out.flush();
                                               }*/
                                           }
                                                   //end

                                             /*  if ((currentFileSize + line.length()) > ls.get(i).filesize) {
                                                   // Close the current output file
                                                   Log.d(line + "Server", "File" + x + "splitted successfully.");

                                                   writer.close();
                                                   byte byteArrray[] = tempstr.getBytes();
                                                   ls.get(i).dout.writeInt(byteArrray.length);
                                                   ls.get(i).dout.write(byteArrray);
                                                   Log.d("Server", "File" + byteArrray.length + "splitted successfully.");
                                                   ls.get(i).dout.flush();
                                                   // Increase the file count and create a new output file and writer
                                                   i++;
                                                   s = "/sdcard/temp10" + i + ".txt";
                                                   outputFile = new File(s);
                                                   writer = new BufferedWriter(new FileWriter(outputFile));
                                                   tempstr = "";
                                                   // Reset the current file size to 0
                                                   currentFileSize = 0;
                                               }
                                               tempstr += line + "\n";
                                               writer.write(line);
                                               writer.newLine();
                                               currentFileSize += line.length();


                                           }
                                           byte byteArrray[] = tempstr.getBytes();
                                           ls.get(i).dout.writeInt(byteArrray.length);
                                           ls.get(i).dout.write(byteArrray);
                                           Log.d("Server", "File" + byteArrray.length + "splitted successfully.");
                                           //ls.get(i).dout.flush();
                                           //  ls.get(i).dout.write(tempstr.getBytes(),0,tempstr.getBytes().length);
                                           ls.get(i).dout.flush();
                                           writer.close();
                                           reader.close();
                                       } */
                                           catch (IOException e) {
                                               e.printStackTrace();
                                           }

                                             Log.d("Server","File splitted successfully.");















                /*try {
                  //  FileInputStream fis = new FileInputStream("/sdcard/temperature.txt");
                   // RandomAccessFile input = new RandomAccessFile("/sdcard/temperature.txt", "r");
                   // long inputLength = input.length();
                    int read_bytes;
                    long start = 0;
                    long end = 0;










                    while ( fis.available() > 0 && x<hs.size()) {


                        s = "/sdcard/temp10" +x+ ".txt";
                        String h="";
                        FileOutputStream fos = new FileOutputStream(s);

                        //  while (y <= k && fis.available() != 0) {
                        read_bytes = fis.read(ls.get(x).b, 0, ls.get(x).b.length);
                        h+=new String(ls.get(x).b);
                        y = y + read_bytes;
                        fos.write(ls.get(x).b, 0, read_bytes);
                        fos.close();
                        //  }
                        //  tvMessages.setText(h);
                        //System.out.println("Part "+x+" created.");

                        Log.d("Server",String.valueOf(x));
// to send file to other client
                        ls.get(x).dout.write(ls.get(x).b,0,ls.get(x).b.length);
                        ls.get(x).dout.flush();


//                        total[x] = total[x] + 1;
                        x++;
                    }


                    Log.d("Server","File splitted successfully.");
                    fis.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/




















          /*      String g="",m="$";
                try {
                    BufferedReader br = new BufferedReader(rdr);
                    while ((g = br.readLine()) != null) {
                        m += g;
                    }
                }catch(Exception e)
                {

                }

                if (!m.isEmpty()) {
                   // new Thread(new Thread3(message,ls)).start();
                    tvMessages.append("server: " + m + " ");
                    etMessage.setText("");
                    for(ClientHandler ch:ls)
                    {
                        try {
                            ch.dout.writeUTF(m);
                            ch.dout.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }



                }*/
                                       }
        });
    }

    private String getLocalIpAddress() throws UnknownHostException {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        assert (wifiManager!=null);
        //  assertNull(message);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipInt = wifiInfo.getIpAddress();
        return InetAddress.getByAddress(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(ipInt).array()).getHostAddress();
    }


    //finding gcd
    static int gcd(int a, int b)
    {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }

    // Function to find gcd of array of
    // numbers
    static int findGCD(ArrayList<Integer> ls)
    {
        int result = ls.get(0);
        for (int element: ls){
            result = gcd(result, element);

            if(result == 1)
            {
                return 1;
            }
        }

        return result;
    }
    // over finding gcd

    private DataOutputStream output;
    private DataInputStream input;
    HashSet<String> hs=new HashSet<String>();
    int k=0;
    long hi=0;
    long rtt;
    String json="";
    class Thread1 implements Runnable {

        @Override
        public void run() {

            try {
                serverSocket = new ServerSocket(SERVER_PORT);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvMessages.setText("Not connected");
                        tvIP.setText("IP: " + SERVER_IP);
                        tvPort.setText("Port: " + String.valueOf(SERVER_PORT));
                    }
                });
                while (true) {
                    Socket socket;
                    try {
                        socket = serverSocket.accept();
                        hs.add(socket.getInetAddress().toString());

                        k++;
                        if(socket!=null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvMessages.setText("Connected "+hs.size() +"devices");
                                }
                            });
                            new Thread(new ClientHandler(socket,k)).start();}
                        // new Thread(new Thread2(k,socket)).start();}
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }catch(IOException e){
                e.printStackTrace();
            }

        }
    }
    double avg;
    long total_numbers;
    int threadcount=0;
    Object lock=new Object();
    double mini=Double.POSITIVE_INFINITY;
    String key1;
    class ClientHandler implements Runnable
    {
        PrintWriter out;
        DataOutputStream dout;
        DataInputStream din;
        Socket g;
        int sid;
        int windows;
        long rtt;
        int num_process;
        int k;
        byte b[];
        int filesize;
        ArrayList<String> chunkseq =new ArrayList<String>();

        ClientHandler(Socket s,int p)
        {
            this.g=s;
            try {
                out= new PrintWriter(g.getOutputStream(), true);
                dout=new DataOutputStream(g.getOutputStream());
                din=new DataInputStream(g.getInputStream());
                num_process=din.readInt();
                processors.add(num_process);
                ls.add(this);




                try {
                    Log.d("Reply",g.getInetAddress()+"");
                    long startTime = System.nanoTime();
                    if (g.getInetAddress().isReachable(3000)) {
                        long elapsedTime = System.nanoTime() - startTime;
                        rtttimers.add(elapsedTime);
                      /*  runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvMessages.append("\nReply from " + g.getInetAddress().getHostAddress() + ": time=" + (elapsedTime/1000000) + "ms\n");
                            }
                        });*/

                        Log.d("Reply","Reply from " + g.getInetAddress().getHostAddress() + ": time=" + (elapsedTime/1000000) + "ms");
                    } else {
                        Log.d("Reply","Request timed out.");
                    }
                } catch (UnknownHostException e) {
                  //  System.err.println("Unable to resolve host " + args[0]);
                } catch (IOException e) {
                    //System.err.println("Error sending/receiving packet: " + e.getMessage());
                }




            } catch (IOException e) {
                e.printStackTrace();
            }
            k=p;
        }
        @Override
        public void run() {
   //   while (true) {
            // long h=0;
            // String message = din.readUTF();

            try {

                //hi+=din.readLong();


                int length = din.readInt();
                byte b[]=new byte[length];
                din.read(b,0,b.length);



                //  byte b[] = new byte[length];
                //  din.read(b, 0, b.length);

                json = new String(b);
                Log.d("json",json);
                ObjectMapper mapper = new ObjectMapper();
                HashMap<String, Double> jsondata = mapper.readValue(json, HashMap.class);
                for (String key : jsondata.keySet()) {
                    double value = jsondata.get(key);
                   if(value<mini)
                   {
                       mini=value;
                       key1=key;
                   }
                }
            } catch (JsonMappingException jsonMappingException) {
                jsonMappingException.printStackTrace();
            } catch (JsonParseException jsonParseException) {
                jsonParseException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        sum_count(json,++threadcount);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    // tvMessages.append(String.valueOf(hi)+"\n");

                }
            });




            // }

        }

    }
    public void sum_count(String json,int tcount) throws JsonProcessingException {
      //  combinejson += json;
        if (tcount == hs.size()) {
            // tvMessages.append("\n" + combinejson + "\n");
          //  tvMessages.append("\n" + String.valueOf(wordCount.size()) + "\n");
           /* for (String number : wordCount.keySet())
            {
                avg=avg+(Double.parseDouble(number)*(wordCount.get(number)));
                total_numbers+=wordCount.get(number);
            }
            avg=avg/total_numbers;*/



          //  ObjectMapper objectMapper = new ObjectMapper();
           // String finalstr = objectMapper.writeValueAsString(wordCount);
            tvMessages.append("\n" + mini+" "+key1 +"to"+ "\n");
            long end = System.nanoTime();
            tvMessages.append("\n Time = " + String.valueOf((end-start1)) + "s\n");
         //   tvMessages.append("\n" + String.valueOf(avg) + "\n");
        }
    }

































   /* private class Thread2 implements Runnable {
        int k;
        Socket socket;
        Thread2(int p,Socket s){
            k=p;
            socket=s;
        }
        @Override
        public void run() {
            try {
                input = new DataInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (true) {
                try {
                    final String message = input.readUTF();
                    if (message!= null)
                    {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvMessages.setText("client"+k+":" + message + " ");
                            }
                        });
                    }
                   *//* if(message==null)
                    {
                        Thread1 = new Thread(new Thread1());
                        Thread1.start();
                        return;
                    }*//*
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    class Thread3 implements Runnable {
        private String message;
        ArrayList<DataOutputStream> list;
        Thread3(String message,ArrayList<DataOutputStream> gs) {
            this.message = message;list=gs;

        }
        @Override
        public void run() {

            try {

                for( DataOutputStream ds:ls) {
                    ds.writeUTF(message);
                    ds.flush();


                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvMessages.append("server: " + message + " ");
                        etMessage.setText("");
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }*/

}