package com.example.questionnaire.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.questionnaire.entity.Questionnaire;
import com.example.questionnaire.vo.QnQuVo;

@Repository
public interface QuestionnaireDao extends JpaRepository<Questionnaire, Integer>{

	/**取的最新一筆資料:撈取全部資料後倒敘,最新的那筆資料會變成第一筆資料**/  //使用斜線星星可讓該程式碼移至該位子時顯示其註解
//	public Questionnaire findTopByOrderByIdDesc();
	
	//In
	public List<Questionnaire> findByIdIn(List<Integer> idList);

	//Containing模糊搜尋
	public List<Questionnaire> findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(String title,LocalDate startDate,LocalDate endDate);
	
	public List<Questionnaire> findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqualAndPublishedTrue(String title,LocalDate startDate,LocalDate endDate);

//==========================================================================================================================================================================//	
	//SQL語法練習 insert跟update強制要加上@Modifying,@Transactional
	@Modifying      //value指的是欄位對應的值
	@Transactional  //insert的限制:nativeQuery = true,要加上nativeQuery時,要寫value =
	@Query(value = "insert into questionnaire (title,description,is_published,start_date,end_date)"
			+ "values(:title, :desp, :isPublished, :startDate, :endDate)", nativeQuery = true) //用冒號對應到@Param內的值
	public int insert( // 帶入資料表的參數↓
			@Param("title") String title, //
			@Param("desp")String description, // 
			@Param("isPublished")boolean isPublished, //
			@Param("startDate")LocalDate startDate, //
			@Param("endDate")LocalDate endDate); //用int因insert成功失敗都會回int
	
	
	//insertData
	@Modifying 		//用nativeQuery questionnaire對應資料庫表的名稱,其餘對應到資欄位名稱
	@Transactional  //insert的限制:nativeQuery = true,要加上nativeQuery時,要寫value =
	@Query(value = "insert into questionnaire (title,description,is_published,start_date,end_date)"
			+ " values (?1, ?2, ?3, ?4, ?5)", nativeQuery = true) //用冒號對應到@Param內的值
	public int insertData( // 帶入資料表的參數↓
			String title, // ?1 用問號?去對應相對應位子 
			String description, // ?2
			boolean isPublished, // ?3
			LocalDate startDate, // ?4
			LocalDate endDate); // ?5 用int因insert成功失敗都會回int
			
//=========================================SQL語法========================================================//	
//	update update語法為:update 資料表名稱, set 資料表欄位 where
	@Modifying
	@Transactional
	@Query(value = "update questionnaire set title = :title, description = :desp"
			+ "where id = :id", nativeQuery = true) //where條件一定要加不加全改
	public int update(
			@Param("id")int id, //
			@Param("title")String title, //
			@Param("desp")String description); 
	
	/**
	 *不寫 nativeQuery 等同於 nativeQuery = false
	 *語法鐘錶的名稱要變稠entity的class名稱 : 欄位名稱要變成屬性名稱
	 **/		
	@Modifying(clearAutomatically = true) //(clearAutomatically = true)清除暫存資料
	@Transactional    
	@Query(value = "update Questionnaire set title = :title, description = :desp, startDate = :startDate"  
			+ " where id = :id") // 不寫 nativeQuery 等同於 nativeQuery = false,where條件一定要加,不加全改,用到where要加空白鍵
	public int updateDate(
			@Param("id")int id, //
			@Param("title")String title, //
			@Param("desp")String description, 
			@Param("startDate")LocalDate startDate); 
		
//==================================select=================================//	
	
	@Query(value = "select * from  questionnaire " 
			+ " where start_date > :startDate" , nativeQuery = true)
	public List<Questionnaire> findByStartDate(@Param("startDate") LocalDate startDate);
	
//	
	@Query(value = "select new  Questionnaire (id, title, description, published, startDate, endDate)"  
			+ " from Questionnaire where startDate > :startDate")
	public List<Questionnaire> findByStartDate1(@Param("startDate") LocalDate startDate);
	
//  nativeQuery = false, select的欄位要使用建構方法的方式,且 Entity 中也要有對應的建構方法
	@Query(value = "select new  Questionnaire (id, title, published)"  
			+ " from Questionnaire where start_date > :startDate")
	public List<Questionnaire> findByStartDate2(@Param("startDate") LocalDate startDate);
	
//  使用別名,語法 as 取別名用 as 自定義 名(qu)	,對entity做操作,對entity取一個別名
	@Query(value = "select qu from Questionnaire as qu"
			+ " where startDate > :startDate or published = :isPublished ")
	public List<Questionnaire> findByStartDate3(
			@Param("startDate") LocalDate startDate, 
			@Param("isPublished")boolean published);

//  order by		
	@Query(value = "select qu from Questionnaire as qu"
			+ " where startDate > :startDate or published = :isPublished order by id desc",nativeQuery = true)
	public List<Questionnaire> findByStartDate4(
			@Param("startDate") LocalDate startDate, 
			@Param("isPublished")boolean published);
	
//	order by + limit
//	1. limit語法只能使用在 nativeQuery = true 
//	2. limit要放在語法最後
	@Query(value = "select * from questionnaire as qu"
			+ " where start_date > :startDate or is_published = :isPublished order by id desc limit :num"
			,nativeQuery = true)
	public List<Questionnaire> findByStartDate5(
			@Param("startDate") LocalDate startDate, 
			@Param("isPublished")boolean published,
			@Param("num")int limitnum);
	
//========================================================================================================//
	@Query(value = "select * from questionnaire "
			+ " limlt :startIndex, :limitNum", nativeQuery = true)
	public List<Questionnaire> findWithLimitAndStartIndex(
			@Param("startIndex")int startIndex,//
			@Param("limitNum")int limitNum);//
	
	
	//like
	@Query(value = "select * from questionnaire " 
			+ " where title like %:title%", nativeQuery = true)
	public List<Questionnaire> searchTitleLike(@Param("title")String title);
	
	
	//regexp 
	@Query(value = "select * from questionnaire " 
			+ " where title like :title", nativeQuery = true)
	public List<Questionnaire> searchTitleLike2(@Param("title")String title);
	
	
	//regexp or
	@Query(value = "select * from questionnaire " 
			+ " where description regexp :keyword1|:keyword2", nativeQuery = true)
	public List<Questionnaire> searchDescriptionContaining(
			@Param("keyword1")String keyword1,
			@Param("keyword2")String keyword2);
	
	
	//regexp or ,regexp只能用在nativeQuery = true的時候
	@Query(value = "select * from questionnaire " 
			+ " where description regexp concat(:keyword1, '|', :keyword2)", nativeQuery = true)
	public List<Questionnaire> searchDescriptionContaining2(
			@Param("keyword1")String keyword1,
			@Param("keyword2")String keyword2);
	
//=================================join==================================//	

	@Query("select new com.example.questionnaire.vo.QnQuVo("
			+ " qn.id, qn.title, qn.description, qn.published, qn.startDate, qn.endDate,"
			+ " q.quId, q.title, q.optionType, q.necessary,q.option)"
			+ " from Questionnaire as qn join Question as q on qn.id = q.qnId")
	public List<QnQuVo> selectJoinQnQu();
	
	
	@Query("select new com.example.questionnaire.vo.QnQuVo("
			+ " qn.id, qn.title, qn.description, qn.published, qn.startDate, qn.endDate,"
			+ " q.quId, q.title, q.optionType, q.necessary,q.option)"
			+ " from Questionnaire as qn join Question as q on qn.id = q.qnId"
			+ " where qn.title like %:title% and qn.startDate >= :startDate and qn.endDate <= :endDate")
	public List<QnQuVo> selectFuzzy(		
			@Param("title")String title,//
			@Param("startDate")LocalDate startDate,
			@Param("endDate")LocalDate endDate);

	
}

