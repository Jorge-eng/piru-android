package is.hello.piru.api.model;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import is.hello.piru.api.SuripuEndpoints;
import retrofit.mime.TypedOutput;

public class OAuthCredentials implements TypedOutput {
    public final SuripuEndpoints endpoints;
    public final String username;
    public final String password;
    private final ByteArrayOutputStream outputStream;

    public OAuthCredentials(@NonNull SuripuEndpoints endpoints,
                            @NonNull String username,
                            @NonNull String password) {
        if (TextUtils.isEmpty(username))
            throw new IllegalArgumentException("username cannot be omitted");

        if (TextUtils.isEmpty(password))
            throw new IllegalArgumentException("password cannot be omitted");

        this.endpoints = endpoints;
        this.username = username;
        this.password = password;
        this.outputStream = new ByteArrayOutputStream();

        generate();
    }


    private void addField(@NonNull String name, @NonNull String value) {
        try {
            if (outputStream.size() > 0)
                outputStream.write('&');

            String pair = Uri.encode(name) + "=" + Uri.encode(value);
            outputStream.write(pair.getBytes("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void generate() {
        addField("grant_type", "password");
        addField("client_id", endpoints.getClientId());
        addField("client_secret", endpoints.getClientSecret());
        addField("username", username);
        addField("password", password);
    }

    @Override
    public String fileName() {
        return null;
    }

    @Override
    public String mimeType() {
        return "application/x-www-form-urlencoded";
    }

    @Override
    public long length() {
        return outputStream.size();
    }

    @Override
    public void writeTo(OutputStream out) throws IOException {
        out.write(outputStream.toByteArray());
    }
}
