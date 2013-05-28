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
    JFrame jf = new JFrame("IP Server v0.01");
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

    groupBoxEncryption_IPv4Address_TextArea.add(scroll_IPv4Address_TextArea);
    scroll_IPv4Address_TextArea.setBounds(10, 45, 300, 100);
    scroll_IPv4Address_TextArea.setVisible(true);
    jf.add(scroll_IPv4Address_TextArea);

    groupBoxEncryption_ServerPrint_TextArea.add(scroll_ServerPrint_TextArea);
    scroll_ServerPrint_TextArea.setBounds(10, 220, 500, 220);
    scroll_ServerPrint_TextArea.setVisible(true);
    jf.add(scroll_ServerPrint_TextArea);

    //#############################################

    int JButton_Width = 110;
    int JButton_Height = 30;
    JButton Button_RunServer = new JButton("RunServer");
    Button_RunServer.setBounds(10, 160, JButton_Width, JButton_Height);
    Button_RunServer.addActionListener(new Button_RunServer_Handler());
    jf.add(Button_RunServer);

    JButton Button_GetIPv4Address_list = new JButton("Get IPv4");
    Button_GetIPv4Address_list.setBounds(10, 10, JButton_Width, JButton_Height);
    Button_GetIPv4Address_list.addActionListener(new Button_GetIPv4Address_list_Handler());
    jf.add(Button_GetIPv4Address_list);

    //#############################################

    jf.setVisible(true);
}

class Button_RunServer_Handler implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {



        try {
            Server_Socket = new ServerSocket(2222);
        } catch (IOException ex) {
            System.out.println(ex);
        }

        Tr2 = new Thread(new MyTread2());
        Tr2.start();


    }
}

class Button_GetIPv4Address_list_Handler implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        IPv4Address_TextArea.setText("");
        IPv4Address();
    }
}

public class MyTread2 implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 1000000; i++) {
            try {
                ServerStatus = 1;
                //System.out.println("Server: waiting for connections  !!!");
                ServerCurentStatus_label.setText("Waiting For Connections  !!!");
                //ServerSocket ss = new ServerSocket(2222);
                //System.out.println("Server: client is connected");
                Socket socket = Server_Socket.accept();
                
                ServerCurentStatus_label.setText("Connected with "+socket.getRemoteSocketAddress().toString()+"/");
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Writer output = new OutputStreamWriter(socket.getOutputStream());
                String line;
                int a = 0;
                while ((line = input.readLine()) != null) {
                    //System.out.println(line);
                    a++;
                    if (a == 12) {
                        //ServerPrint_TextArea.setText("");
                        a = 0;
                    }
                    //ServerPrint_TextArea.append(line + "\n");
                    TextField1.setText(line);
                    output.write(line + "\n");
                    output.flush();
                }
                socket.close();
                ServerCurentStatus_label.setText("Stopped !!!");
                ServerStatus = 0;
            } catch (IOException ex) {
                System.out.println(ex);
                ServerStatus = 0;
            }

        }
    }
}

public void IPv4Address() {
    String ip;
    try {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface iface = interfaces.nextElement();
            // filters out 127.0.0.1 and inactive interfaces
            if (iface.isLoopback() || !iface.isUp()) {
                continue;
            }

            Enumeration<InetAddress> addresses = iface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress addr = addresses.nextElement();
                ip = addr.getHostAddress();
                // System.out.println(iface.getDisplayName() + " " + ip);
                //System.out.println(ip);
                IPv4Address_TextArea.append(ip + "\n");
            }
        }
    } catch (SocketException e) {
        throw new RuntimeException(e);
    }
}
}