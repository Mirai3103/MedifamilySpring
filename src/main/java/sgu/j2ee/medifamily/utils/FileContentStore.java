package sgu.j2ee.medifamily.utils;

import java.util.UUID;

import org.springframework.content.s3.store.S3ContentStore;
import org.springframework.stereotype.Component;

import sgu.j2ee.medifamily.entities.FileDocument;

//public interface FileContentStore extends ContentStore<FileDocument, String> {
//}

@Component
public interface FileContentStore extends S3ContentStore<FileDocument, UUID> {
}