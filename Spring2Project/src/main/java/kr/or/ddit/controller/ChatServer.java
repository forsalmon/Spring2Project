package kr.or.ddit.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

// 웹 소켓 서버 요청명을 지정, 해당 요청명으로 접속하는 클라이언트를 이 클래스가 처리하게 됨.
// ws://localhost:80/ChatingServer
@ServerEndpoint("/ChatingServer")
public class ChatServer {
	private static Set<Session> clients
		= Collections.synchronizedSet(new HashSet<Session>());
	// 새로 접속한 클라이언트의 세션을 저장할 컬렉션을 생성한다.
	// Collections.synchronizedSet은 멀티스레드 환경에서도 안전한 set컬렉션을 생성한다.
	// ArrayList는 보안에 취약하다고 한다.
	// 즉 여러 클라이언트가 동시에 접속해도 문제가 생기지 않도록 동기화
	
	@OnOpen // 클라이언트 접속시 실행, 단순하게 clients의 컬렉션에 클라이언트의 세션을 추가
	public void onOpen(Session session) {
		clients.add(session);
		System.out.println("웹 소켓 연결: " + session.getId());
	}
	
	// 클라이언트로부터 메시지를 받으면 실행된다. 클라이언트가 보낸 메시지와 클라이언트와 연결된
	// 세션이 매개변수로 넘어온다.
	@OnMessage 
	public void onMessage(String message, Session session) throws IOException {
		System.out.println("메시지 전송 : " + session.getId() + " : " + message);
		synchronized (clients) {
			for(Session client : clients) { // 모든 클라이언트에게 메시지 전달
				if(!client.equals(session)) {
					// 단 메세지를 보낸 클라이언트는 제외하고 전달한다.
					client.getBasicRemote().sendText(message);
				}
			}
		}
	}
	
	@OnClose // 클라이언트와의 연결이 끊기면 실행
	public void onClose(Session session) {
		clients.remove(session);
		System.out.println("웹 소켓 종료 : " + session.getId());
	}
	
	@OnError // 에러 발생시 실행
	public void onError(Throwable e) {
		System.out.println("에러 발생");
		e.printStackTrace();
	}
	
}
