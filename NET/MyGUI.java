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
	
	int mmm = 0 ;
//#############################################
PrintStream out = null;
BufferedReader in = null;
//#############################################
Thread Tr2 = null;
int ClientStatus = 0;
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
//public JLabel ServerStatus_label = new JLabel();
//public JLabel ServerCurentStatus_label = new JLabel();

public JLabel label_RemoteIP = new JLabel();
public JLabel label_RemotePORT = new JLabel();
public JLabel label_ClientSTATUS = new JLabel();
public JLabel label_ClientSTATUS_Text = new JLabel();
//#############################################
JButton Button_Connect;
JButton Button_DataSend;
//#############################################
//#############################################


public MyGUI() {
    JFrame jf = new JFrame("IP Client v0.01");
    jf.setLayout(null);
    jf.setBounds(100, 100, 535, 500);
    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    //#############################################
    
    int JButton_Width = 110;
    int JButton_Height = 30;

    //#############################################
    
    int MyX1 = 10;
    int MyY1 = 10;
    
    label_ClientSTATUS.setBounds(MyX1, MyY1, 150, 25);
    label_ClientSTATUS.setText("Client Status :");
    jf.add(label_ClientSTATUS);
    
    label_ClientSTATUS_Text.setBounds(MyX1+105, MyY1, 400, 25);
    label_ClientSTATUS_Text.setText("No Status Information");
    label_ClientSTATUS_Text.setForeground(Color.RED);
    jf.add(label_ClientSTATUS_Text);
  
    //#############################################
    
    int MyX = 10;
    int MyY = 40;
    
    Button_Connect = new JButton("Connect");
    Button_Connect.setBounds(MyX, MyY+8, JButton_Width, JButton_Height+6);
    Button_Connect.addActionListener(new Button_Connect_Handler());
    jf.add(Button_Connect); 
    
    label_RemoteIP.setBounds(MyX +130, MyY, 100, 25);
    label_RemoteIP.setText("IP");
    jf.add(label_RemoteIP);
    
    TextField_IP.setBounds(MyX+130, MyY+20, 100, 25);
    TextField_IP.setText("127.0.0.1");
    jf.add(TextField_IP);
    
    label_RemotePORT.setBounds(MyX+240, MyY, 100, 25);
    label_RemotePORT.setText("PORT");
    jf.add(label_RemotePORT);  
    
    TextField_PORT.setBounds(MyX+240, MyY+20, 100, 25);
    TextField_PORT.setText("2222");
    jf.add(TextField_PORT);  
    
   
 
    //#############################################

    
    
    
    TextField1.setBounds(10, 195, 500, 25);
    jf.add(TextField1);
   
    //#############################################

    groupBoxEncryption_ServerPrint_TextArea.add(scroll_ServerPrint_TextArea);
    scroll_ServerPrint_TextArea.setBounds(10, 220, 500, 220);
    scroll_ServerPrint_TextArea.setVisible(true);
    jf.add(scroll_ServerPrint_TextArea);

    //#############################################


 
    Button_DataSend = new JButton("DataSend");
    Button_DataSend.setBounds(10, 120, JButton_Width, JButton_Height);
    Button_DataSend.addActionListener(new Button_DataSend_Handler());
    Button_DataSend.setEnabled(false);
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
    	
    	if (ClientStatus==0){
    	
    		Tr2 = new Thread(new MyTread2());
    		Tr2.start();
    		
    	}else{
			out.close();
			try {
				in.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Button_Connect.setText("Connect");
			Button_DataSend.setEnabled(false);
			ClientStatus=0;
    	}
        
        
        
    }
}



public class MyTread2 implements Runnable {

    @Override
    public void run() {

            try {
            	
            	int Remote_PORT = Integer.parseInt(TextField_PORT.getText());
            	String Remote_IP = TextField_IP.getText();
            	
            	
    			Socket client=new Socket(Remote_IP, Remote_PORT);
    			
        		ClientStatus = 1 ;			
    			label_ClientSTATUS_Text.setText("Connected with "+client.getRemoteSocketAddress().toString()+"/");
    			Button_Connect.setText("Disconnect");
    			Button_DataSend.setEnabled(true);
    			
    			out=new PrintStream(client.getOutputStream());
    			in= new BufferedReader(new InputStreamReader(client.getInputStream()));
    			
                String line;
                int a = 0;
                while ((line = in.readLine()) != null) {

                    //ServerPrint_TextArea.append(line + "\n");
                    TextField1.setText(line);
                    out.print(a + " xxxxxxxxxxxxxxxx \n"); a++;
                    out.flush();
                }			
    			
            	
            } catch (IOException ex) {
            	label_ClientSTATUS_Text.setText(ex.getMessage());
                System.out.println(ex);
                //ClientStatus = 0;
            }
    }
}


}