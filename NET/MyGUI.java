package NET;


//import org.apache.http.conn.util.InetAddressUtils;
//import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MyGUI {

	//Writer output = null	;
	//Socket s= null	;
	PrintStream out = null	;
	int mmm = 0 ;
	
//#############################################
Thread Tr2 = null;
int ServerStatus = 0;
ServerSocket Server_Socket;
//#############################################
public JTextArea IPv4Address_TextArea = new JTextArea();
public JPanel groupBoxEncryption_IPv4Address_TextArea = new JPanel();
public JScrollPane scroll_IPv4Address_TextArea = new JScrollPane(IPv4Address_TextArea);
//#############################################
public JTextArea ServerPrint_TextArea = new JTextArea();
public JPanel groupBoxEncryption_ServerPrint_TextArea = new JPanel();
public JScrollPane scroll_ServerPrint_TextArea = new JScrollPane(ServerPrint_TextArea);
//#############################################
public JTextField TextField1 = new JTextField();
public JTextField TextField_IP = new JTextField();
public JTextField TextField_PORT = new JTextField();
//#############################################
//public JTextField ServerCurentStatus_TextField = new JTextField();
//#############################################
public JLabel ServerStatus_label = new JLabel();
public JLabel ServerCurentStatus_label = new JLabel();

public JLabel RemoteIP_label = new JLabel();
public JLabel RemotePORT_label = new JLabel();
//#############################################
//#############################################
//#############################################


public MyGUI() {
    JFrame jf = new JFrame("IP Client v0.01");
    jf.setLayout(null);
    jf.setBounds(100, 100, 535, 500);
    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    //#############################################

    RemoteIP_label.setBounds(10, 100, 400, 25);
    RemoteIP_label.setText("IP");
    jf.add(RemoteIP_label);
    
    
    ServerStatus_label.setBounds(125, 172, 400, 25);
    ServerStatus_label.setText("Server Status :");
    jf.add(ServerStatus_label);
    
    ServerCurentStatus_label.setBounds(235, 172, 400, 25);
    ServerCurentStatus_label.setForeground(Color.RED);
    ServerCurentStatus_label.setText(".....");
    jf.add(ServerCurentStatus_label);       
    //#############################################
    
    int MyX = 10;
    int MyY = 60;
    
    RemoteIP_label.setBounds(MyX, MyY, 100, 25);
    RemoteIP_label.setText("IP");
    jf.add(RemoteIP_label);
    
    TextField_IP.setBounds(MyX, MyY+20, 100, 25);
    TextField_IP.setText("127.0.0.1");
    jf.add(TextField_IP);
    
    RemotePORT_label.setBounds(MyX+110, MyY, 100, 25);
    RemotePORT_label.setText("PORT");
    jf.add(RemotePORT_label);  
    
    TextField_PORT.setBounds(MyX+110, MyY+20, 100, 25);
    TextField_PORT.setText("2222");
    jf.add(TextField_PORT);  
    //#############################################

    
    
    
    TextField1.setBounds(10, 195, 500, 25);
    jf.add(TextField1);
   
    //#############################################
    
    //ServerCurentStatus_TextField.setBounds(115, 160, 395, 31);
    //jf.add(ServerCurentStatus_TextField);
    //ServerCurentStatus_TextField.setText("Server Stopped !!!");
    
    //#############################################

    //groupBoxEncryption_IPv4Address_TextArea.add(scroll_IPv4Address_TextArea);
    //scroll_IPv4Address_TextArea.setBounds(10, 45, 300, 100);
    //scroll_IPv4Address_TextArea.setVisible(true);
    //jf.add(scroll_IPv4Address_TextArea);

    groupBoxEncryption_ServerPrint_TextArea.add(scroll_ServerPrint_TextArea);
    scroll_ServerPrint_TextArea.setBounds(10, 220, 500, 220);
    scroll_ServerPrint_TextArea.setVisible(true);
    jf.add(scroll_ServerPrint_TextArea);

    //#############################################

    int JButton_Width = 110;
    int JButton_Height = 30;
    
    JButton Button_Connect = new JButton("Connect");
    Button_Connect.setBounds(10, 160, JButton_Width, JButton_Height);
    Button_Connect.addActionListener(new Button_Connect_Handler());
    jf.add(Button_Connect);
 
    JButton Button_DataSend = new JButton("DataSend");
    Button_DataSend.setBounds(10, 10, JButton_Width, JButton_Height);
    Button_DataSend.addActionListener(new Button_DataSend_Handler());
    jf.add(Button_DataSend);


    //#############################################

    jf.setVisible(true);
}


class Button_DataSend_Handler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        out.print(" xxxxxxxxxxxxxxxx \n");
        out.flush();
    }
}



class Button_Connect_Handler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        Tr2 = new Thread(new MyTread2());
        Tr2.start();
    }
}



public class MyTread2 implements Runnable {

    @Override
    public void run() {

            try {
            	
            	int Remote_PORT = Integer.parseInt(TextField_PORT.getText());
            	String Remote_IP = TextField_IP.getText();
            	
            	
    			Socket client=new Socket(Remote_IP, Remote_PORT);
    			System.out.println("Client connected ");
    			out=new PrintStream(client.getOutputStream());

    			BufferedReader in= new BufferedReader(new InputStreamReader(client.getInputStream()));
    			
                String line;
                int a = 0;
                while ((line = in.readLine()) != null) {

                    //ServerPrint_TextArea.append(line + "\n");
                    TextField1.setText(line);
                    out.print(a + " xxxxxxxxxxxxxxxx \n"); a++;
                    out.flush();
                }			
    			
            	
            } catch (IOException ex) {
                System.out.println(ex);
                ServerStatus = 0;
            }
    }
}


}