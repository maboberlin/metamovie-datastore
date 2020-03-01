package com.digitalindexing.metamovie.datastore.impl.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import java.io.InputStream;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.gridfs.GridFsResource;

public interface GridFSRepository {
  ObjectId store(InputStream inputStream, String fileName, String contentType, DBObject metaData);

  ObjectId storeAndOverwrite(
      InputStream inputStream, String fileName, String contentType, BasicDBObject basicDBObject);

  GridFSFile getById(String id);

  GridFSFile getByFilename(String filename);

  GridFsResource getResourceById(String id);

  List<GridFSFile> findAll();
}
