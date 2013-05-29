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
//#############################################
//public JTextField ServerCurentStatus_TextField = new JTextField();
//#############################################
public JLabel ServerStatus_label = new JLabel();
public JLabel ServerCurentStatus_label = new JLabel();
//#############################################
//#############################################
//#############################################


public MyGUI() {
    JFrame jf = new JFrame("IP Client v0.01");
    jf.setLayout(null);
    jf.setBounds(100, 100, 535, 500);
    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    //#############################################

    ServerStatus_label.setBounds(125, 172, 400, 25);
    ServerStatus_label.setText("Server Status :");
    jf.add(ServerStatus_label);
    
    ServerCurentStatus_label.setBounds(235, 172, 400, 25);
    ServerCurentStatus_label.setForeground(Color.RED);
    ServerCurentStatus_label.setText(".....");
    jf.add(ServerCurentStatus_label);       
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
            	
    			Socket client=new Socket("localhost",2222);
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