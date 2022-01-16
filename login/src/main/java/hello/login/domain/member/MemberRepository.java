package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    public Member save(Member member){
        member.setId(++sequence);
        log.info("save: member={}", member);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id){
        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId){
/*
        List<Member> all = findAll();
        for (Member m : all) {
            if(m.getLoginId().equals(loginId))
                Optional.of(m);
        }

        // null을 직접반환하는거 보다 Optional로 많이 반환함. 요즘에는
        return Optional.empty();
*/

        // 위 코드와 같은 로직
        return findAll().stream()
                .filter(m-> m.getLoginId().equals(loginId))
                .findFirst();
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
