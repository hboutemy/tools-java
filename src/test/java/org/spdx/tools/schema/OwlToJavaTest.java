/**
 * SPDX-License-Identifier: Apache-2.0
 * Copyright (c) 2023 Source Auditor Inc.
 */
package org.spdx.tools.schema;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.util.Objects;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaSerializer.XmlSchemaSerializerException;

import junit.framework.TestCase;

/**
 * @author gary
 *
 */
public class OwlToJavaTest extends TestCase {
	
	static final String MODEL_FILE_PATH = "testResources" + File.separator + "model.ttl";

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testConvertToJava() throws IOException, XmlSchemaSerializerException, SchemaException {
		OwlToJava otj = null;
		File tempDir = Files.createTempDirectory("spdx_test").toFile();
		try {
			try (InputStream is = new FileInputStream(new File(MODEL_FILE_PATH))) {
				OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
				model.read(is, "", "Turtle");
				otj = new OwlToJava(model);
				otj.generateJavaSource(tempDir);
			}
		} finally {
			assertTrue(deleteDirectory(tempDir));
		}
	}

	/**
	 * @param tempDir
	 */
	private boolean deleteDirectory(File tempDir) {
		File[] files = tempDir.listFiles();
		if (Objects.nonNull(files)) {
			for (File file:files) {
				deleteDirectory(file);
			}
		}
		return tempDir.delete();
	}

}
