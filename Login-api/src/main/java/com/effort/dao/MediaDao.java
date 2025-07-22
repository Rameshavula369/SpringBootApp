package com.effort.dao;

import java.sql.Types;
import java.util.Date;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;


import com.effort.entity.Media;
import com.effort.sqls.Sqls;
import com.effort.util.Api;
@Repository
public class MediaDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public long saveMedia(final Media media){
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(new PreparedStatementCreator() {

//			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(Sqls.INSERT_MEDIA, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, media.getCompanyId());
				ps.setLong(2, media.getEmpId());
				ps.setString(3, media.getMimeType());
				ps.setString(4, media.getLocalPath());
				ps.setString(5, media.getFileName());
				ps.setString(6, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
				ps.setString(7, Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
				
				if(media.getConfig() == null){
					ps.setInt(8, 0);
				}else{
					ps.setInt(8, media.getConfig());
				}
				
				if(Api.isEmptyString(media.getExternalMediaId())){
					ps.setNull(9, Types.VARCHAR);
				}else{
					ps.setString(9, media.getExternalMediaId());
				}
				return ps;
			}
		}, keyHolder);
		
		long mediaId = keyHolder.getKey().longValue();
		media.setId(mediaId);
		
		return mediaId;
	}
	public long saveMediaChecksum(final Media media, final String checksum){
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(new PreparedStatementCreator() {

//			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(Sqls.INSERT_MEDIA_CHECKSUM, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, media.getId());
				ps.setString(2, media.getLocalPath());
				ps.setString(3, checksum);
				return ps;
			}
		}, keyHolder);
		
		long id = keyHolder.getKey().longValue();
		return id;
	}

}
