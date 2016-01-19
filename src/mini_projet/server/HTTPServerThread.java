package mini_projet.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import mini_projet.sax.MDXInterpreter;
import mini_projet.sax.SAXParser;

public class HTTPServerThread extends Thread {

    private Socket s;

    public HTTPServerThread(Socket sock) {
        super();
        this.s = sock;
    }

    public void run() {
        try {
            File f = null;
            InputStream is = this.s.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is,
                    "US-ASCII"));
            String rq = br.readLine();
            if ((rq != null) && (rq.startsWith("GET "))) {
                rq = rq.substring(5, rq.indexOf("HTTP"));

                switch (rq.split("\\?")[0]) {
                    case " ": //caractere quand on met pas index.html
                    case "index.html":
                       f = new File("index.mdx ");
                        if (f.isFile()) {
                            SAXParser parser = new SAXParser(null);
                            parser.parse(f);
                            this.s.getOutputStream().write(MDXInterpreter.getHtml().getBytes());
                        }
                        break;
                    case "detail.html":
                        String id = rq.split("\\?")[1].split("=")[1];
                        id = id.substring(0,id.length()-1);
                        f = new File("detail.mdx");
                        if (f.isFile()) {
                            SAXParser parser = new SAXParser(id);
                            parser.parse(f);
                            this.s.getOutputStream().write(MDXInterpreter.getHtml().getBytes());
                        }
                        break;

                    default:
                        System.out.println("default");
                        break;
                }

            }
            br.close();
        } catch (IOException e) {
        }
        try {
            this.s.close();
        } catch (IOException e) {
        }
    }
}
