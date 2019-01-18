package win.hupubao.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * 签名类型
 */
public enum SignType {
    MD5,
    RSA,
    RSA2,
    UNKNOWN;

    public static SignType getSignType(String signType) {

        Optional<SignType> optionalSignType = Arrays.stream(values())
                .filter(st -> st.name()
                        .equalsIgnoreCase(signType)).findFirst();

        if (optionalSignType.isPresent()) {
            return optionalSignType.get();
        }

        return UNKNOWN;
    }
}
