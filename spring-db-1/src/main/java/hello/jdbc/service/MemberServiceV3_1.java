package hello.jdbc.service;

import hello.jdbc.repository.MemberRepositoryV3;
import hello.jdbc.domain.Member;
import java.sql.Connection;
import java.sql.SQLException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 트랜잭션 - 트랜잭션 매니저
 */
@RequiredArgsConstructor
@Slf4j
public class MemberServiceV3_1 {

//    private final DataSource dataSource;
    private final PlatformTransactionManager transactionManager;
    private final MemberRepositoryV3 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException{

        // 트랜잭션 시작
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            // 비지니스 로직 수행
            bizLogic(fromId, toId, money);
            // 성공시 커밋
            transactionManager.commit(status);
        }catch (Exception e){
            // 실패시 롤백
            transactionManager.rollback(status);
            throw new IllegalStateException(e);
        }
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
