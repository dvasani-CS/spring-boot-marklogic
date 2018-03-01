package com.example.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.DatabaseClientFactory.Authentication;
import com.marklogic.client.document.JSONDocumentManager;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DOMHandle;
import com.marklogic.client.io.InputStreamHandle;

/**
 * DocumentReader illustrates how to read the content of a database document.
 */
public class DocumentRead {
	private static String host = "localhost";

	private static int port = 8011;

	private static String username = "rest-admin";

	private static String password = "x";

	public static void main(String[] args) throws IOException, XPathExpressionException {
		run();
	}

	public static void run() throws IOException, XPathExpressionException {
		System.out.println("example: " + DocumentRead.class.getName());

		// create the client
		DatabaseClient client = DatabaseClientFactory.newClient(host, port, username, password, Authentication.DIGEST);

		setUpExample(client);

		// use either shortcut or strong typed IO
		runShortcut(client);
		runStrongTyped(client);

		tearDownExample(client);

		// release the client
		client.release();
	}

	public static void runShortcut(DatabaseClient client) throws IOException {
		// create a manager for XML documents
		XMLDocumentManager docMgr = client.newXMLDocumentManager();

		// create an identifier for the document
		String docId = "/example/products.json";

		// read the document content
		Document document = docMgr.readAs(docId, Document.class);

		// access the document content
		String rootName = document.getDocumentElement().getTagName();
		System.out.println("(Shortcut) Read " + docId + " content with the <" + rootName + "/> root element");
	}

	public static void runStrongTyped(DatabaseClient client) throws IOException, XPathExpressionException {
		// create a manager for XML documents
		XMLDocumentManager docMgr = client.newXMLDocumentManager();

		// create an identifier for the document
		String docId = "/example/products.json";

		// create a handle to receive the document content
		DOMHandle handle = new DOMHandle();

		// read the document content
		docMgr.read(docId, handle);
		Document document = handle.get();

		// apply an XPath 1.0 expression to the document
		String productName = handle.evaluateXPath("string(/product/name)", String.class);

		// access the document content
		String rootName = document.getDocumentElement().getTagName();
		System.out.println("(Strong Typed) Read /example/" + docId + " content with the <" + rootName
				+ "/> root element for the " + productName + " product");
	}

	// set up by writing document content for the example to read
	public static void setUpExample(DatabaseClient client) throws IOException {
		String filename = "products.json";

		JSONDocumentManager docMgr = client.newJSONDocumentManager();

		String docId = "/example/" + filename;

		InputStream docStream = DocumentRead.class.getClassLoader()
				.getResourceAsStream("sampledata" + File.separator + filename);
		if (docStream == null)
			throw new IOException("Could not read document example");

		InputStreamHandle handle = new InputStreamHandle();
		handle.set(docStream);

		docMgr.write(docId, handle);
	}

	// clean up by deleting the document read by the example
	public static void tearDownExample(DatabaseClient client) {
		JSONDocumentManager docMgr = client.newJSONDocumentManager();

		String docId = "/example/products.json";

		docMgr.delete(docId);
	}
}