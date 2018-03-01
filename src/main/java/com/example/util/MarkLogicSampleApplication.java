package com.example.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.DatabaseClientFactory.Authentication;
import com.marklogic.client.document.JSONDocumentManager;

public class MarkLogicSampleApplication {

	private static String host = "localhost";

	private static int port = 8011;

	private static String username = "admin";

	private static String password = "admin";

	public static void main(String[] args) {
		run(host, port, username, password, Authentication.DIGEST);
	}

	public static void run(String host, int port, String user, String password, Authentication authType) {

		DatabaseClient client = DatabaseClientFactory.newClient(host, port, "products", username, password, authType);
		System.out.println("Connected to: " + client.getDatabase());
		JSONDocumentManager docMgr = client.newJSONDocumentManager();
		String docId = "/examples/products";
		//docMgr.write(docId, new FileHandle().with(new File("/Users/dvasani/Documents/MS/workspace/demo/src/main/resources/sampledata/products.json")).withFormat(Format.JSON));
		JsonNode jsonDocContents = docMgr.readAs(docId, JsonNode.class);
		System.out.println("JSON output: " + jsonDocContents);
		client.release();
	}
}