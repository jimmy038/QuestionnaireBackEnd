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

	/**�����̷s�@�����:����������ƫ�˱�,�̷s��������Ʒ|�ܦ��Ĥ@�����**/  //�ϥα׽u�P�P�i���ӵ{���X���ܸӦ�l����ܨ����
//	public Questionnaire findTopByOrderByIdDesc();
	
	//In
	public List<Questionnaire> findByIdIn(List<Integer> idList);

	//Containing�ҽk�j�M
	public List<Questionnaire> findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(String title,LocalDate startDate,LocalDate endDate);
	
	public List<Questionnaire> findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqualAndPublishedTrue(String title,LocalDate startDate,LocalDate endDate);

//==========================================================================================================================================================================//	
	//SQL�y�k�m�� insert��update�j��n�[�W@Modifying,@Transactional
	@Modifying      //value�����O����������
	@Transactional  //insert������:nativeQuery = true,�n�[�WnativeQuery��,�n�gvalue =
	@Query(value = "insert into questionnaire (title,description,is_published,start_date,end_date)"
			+ "values(:title, :desp, :isPublished, :startDate, :endDate)", nativeQuery = true) //�Ϋ_��������@Param������
	public int insert( // �a�J��ƪ��Ѽơ�
			@Param("title") String title, //
			@Param("desp")String description, // 
			@Param("isPublished")boolean isPublished, //
			@Param("startDate")LocalDate startDate, //
			@Param("endDate")LocalDate endDate); //��int�]insert���\���ѳ��|�^int
	
	
	//insertData
	@Modifying 		//��nativeQuery questionnaire������Ʈw���W��,��l����������W��
	@Transactional  //insert������:nativeQuery = true,�n�[�WnativeQuery��,�n�gvalue =
	@Query(value = "insert into questionnaire (title,description,is_published,start_date,end_date)"
			+ " values (?1, ?2, ?3, ?4, ?5)", nativeQuery = true) //�Ϋ_��������@Param������
	public int insertData( // �a�J��ƪ��Ѽơ�
			String title, // ?1 �ΰݸ�?�h�����۹�����l 
			String description, // ?2
			boolean isPublished, // ?3
			LocalDate startDate, // ?4
			LocalDate endDate); // ?5 ��int�]insert���\���ѳ��|�^int
			
//=========================================SQL�y�k========================================================//	
//	update update�y�k��:update ��ƪ�W��, set ��ƪ���� where
	@Modifying
	@Transactional
	@Query(value = "update questionnaire set title = :title, description = :desp"
			+ "where id = :id", nativeQuery = true) //where����@�w�n�[���[����
	public int update(
			@Param("id")int id, //
			@Param("title")String title, //
			@Param("desp")String description); 
	
	/**
	 *���g nativeQuery ���P�� nativeQuery = false
	 *�y�k�������W�٭n�ܸYentity��class�W�� : ���W�٭n�ܦ��ݩʦW��
	 **/		
	@Modifying(clearAutomatically = true) //(clearAutomatically = true)�M���Ȧs���
	@Transactional    
	@Query(value = "update Questionnaire set title = :title, description = :desp, startDate = :startDate"  
			+ " where id = :id") // ���g nativeQuery ���P�� nativeQuery = false,where����@�w�n�[,���[����,�Ψ�where�n�[�ť���
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
	
//  nativeQuery = false, select�����n�ϥΫغc��k���覡,�B Entity ���]�n���������غc��k
	@Query(value = "select new  Questionnaire (id, title, published)"  
			+ " from Questionnaire where start_date > :startDate")
	public List<Questionnaire> findByStartDate2(@Param("startDate") LocalDate startDate);
	
//  �ϥΧO�W,�y�k as ���O�W�� as �۩w�q �W(qu)	,��entity���ާ@,��entity���@�ӧO�W
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
//	1. limit�y�k�u��ϥΦb nativeQuery = true 
//	2. limit�n��b�y�k�̫�
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
	
	
	//regexp or ,regexp�u��ΦbnativeQuery = true���ɭ�
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

