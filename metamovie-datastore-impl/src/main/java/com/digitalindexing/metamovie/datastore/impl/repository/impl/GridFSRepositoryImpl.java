package com.digitalindexing.metamovie.datastore.impl.repository.impl;

import com.digitalindexing.metamovie.datastore.impl.repository.GridFSRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.gridfs.model.GridFSFile;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GridFSRepositoryImpl implements GridFSRepository {

  @Autowired private GridFsTemplate gridFsTemplate;

  public ObjectId store(
      InputStream inputStream, String fileName, String contentType, DBObject metaData) {
    return this.gridFsTemplate.store(inputStream, fileName, contentType, metaData);
  }

  @Override
  public ObjectId storeAndOverwrite(
      InputStream inputStream, String fileName, String contentType, BasicDBObject basicDBObject) {
    this.gridFsTemplate.delete(new Query(Criteria.where("filename").is(fileName)));
    return store(inputStream, fileName, contentType, basicDBObject);
  }

  public GridFSFile getById(String id) {
    return this.gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
  }

  public GridFSFile getByFilename(String fileName) {
    return gridFsTemplate.findOne(new Query(Criteria.where("filename").is(fileName)));
  }

  public GridFsResource getResourceById(String id) {
    GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
    return gridFsTemplate.getResource(file.getFilename());
  }

  public List<GridFSFile> findAll() {
    List<GridFSFile> result = new ArrayList<>();
    MongoCursor<GridFSFile> cursor = gridFsTemplate.find(null).iterator();
    while (cursor.hasNext()) {
      GridFSFile next = cursor.next();
      result.add(next);
    }
    return result;
  }
}
