package hello.jdbc.service;

import hello.jdbc.Repository.MemberRepositoryV2;
import hello.jdbc.domain.Member;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 트랜잭션 - 파라미터연동, 풀을 연동한 종료
 */
@RequiredArgsConstructor
@Slf4j
public class MemberServiceV2 {

    private final DataSource dataSource;
    private final MemberRepositoryV2 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException{

        Connection con = dataSource.getConnection();
        try {
            // 2. 트랜잭션 시작
            con.setAutoCommit(false);

            // 3. 비지니스 로직 수행
            bizLogic(con, fromId, toId, money);

            // 비지니스 로직 성공시 커밋
            // 6. 트랜잭션 종료
            con.commit();
        }catch (Exception e){
            // 실패시 롤백
            con.rollback();
            throw new IllegalStateException(e);
        }finally {
            // 7. 커넥션 종료
            release(con);
        }
    }

    private void bizLogic(Connection con, String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(con, fromId);
        Member toMember = memberRepository.findById(con, toId);

        memberRepository.update(con, fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(con, toId, toMember.getMoney() + money);
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
