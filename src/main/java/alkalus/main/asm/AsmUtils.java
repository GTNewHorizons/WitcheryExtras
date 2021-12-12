package alkalus.main.asm;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;

import alkalus.main.core.util.Logger;

public class AsmUtils {

	public static byte[] getClassBytes(String aFullClassName) {
		String aResourcePath = aFullClassName.replace('.', '/');
		Logger.ASM("Trying to get class byte[] for: "+aFullClassName);
		Logger.ASM("Using resource path: "+aResourcePath);
		byte[] aData = null;
		try {
			aData = Files.readAllBytes(Paths.get(AsmUtils.class.getClassLoader().getResource(aResourcePath).toURI()));
		}
		catch (IOException | URISyntaxException e) {
			e.printStackTrace();
			try {
				aData = IOUtils.toByteArray(AsmUtils.class.getClassLoader().getResourceAsStream(aResourcePath));
			}
			catch (IOException r) {
				r.printStackTrace();
			}
		}
		return aData;
	}
	
}
