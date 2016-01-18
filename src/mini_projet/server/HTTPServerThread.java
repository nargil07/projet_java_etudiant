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
			InputStream is = this.s.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					"US-ASCII"));
			String rq = br.readLine();
			if ((rq != null) && (rq.startsWith("GET "))) {
				rq = rq.substring(5,rq.indexOf("HTTP"));
				File f = new File(rq);
				if (f.isFile()) {
					SAXParser parser = new SAXParser(f);
					this.s.getOutputStream().write(MDXInterpreter.getHtml().getBytes());
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