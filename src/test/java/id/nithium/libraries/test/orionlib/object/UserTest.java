package id.nithium.libraries.test.orionlib.object;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class UserTest {

    @NonNull
    private UUID uuid;

    @NonNull
    private String username;
}
