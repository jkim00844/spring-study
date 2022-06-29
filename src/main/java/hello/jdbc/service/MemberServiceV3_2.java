package hello.jdbc.service;

import hello.jdbc.Repository.MemberRepositoryV3;
import hello.jdbc.domain.Member;
import java.sql.Connection;
import java.sql.SQLException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 트랜잭션 - 트랜잭션 템플릿
 */
@Slf4j
public class MemberServiceV3_2 {

//    private final DataSource dataSource;
    private final TransactionTemplate txTemplate;
    private final MemberRepositoryV3 memberRepository;

    public MemberServiceV3_2(PlatformTransactionManager transactionManager,
        MemberRepositoryV3 memberRepository) {
        // 외부에서 PlatformTransactionManager를 주입받아
        // 내부에서 TransactionTemplate를 사용한다.
        this.txTemplate = new TransactionTemplate(transactionManager);
        this.memberRepository = memberRepository;
    }

    public void accountTransfer(String fromId, String toId, int money) throws SQLException{

        txTemplate.executeWithoutResult((status) -> {
            // executeWithoutResult 여기에 트랜잭션 시작, 성공시 커밋, 실패시 롤백에 관한 로직, 코드가 다 들어있다.
            // 비지니스 로직
            try {
                bizLogic(fromId, toId, money);
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        });
    }

    private void bizLogic(String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.update(fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(toId, toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if(toMember.getMemberId().equals("ex")){
            throw new IllegalStateException("이체중 예외 발생");
        }
    }

    private void release(Connection con) {
        if(con != null){
            try {
                // 커넥션 풀에게 반납할때 autoCommit = true 로 설정
                // 다른 로직에서 커넥션 맺을 때 보통 디폴트 값이 true 이기 때문에
                con.setAutoCommit(true);
                con.close();
            }catch (Exception e){
                log.info("error", e);
            }
        }
    }

}
