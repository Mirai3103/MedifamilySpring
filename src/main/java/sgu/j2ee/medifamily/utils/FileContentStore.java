package sgu.j2ee.medifamily.utils;

import org.springframework.content.commons.store.ContentStore;

import sgu.j2ee.medifamily.entities.FileDocument;

public interface FileContentStore extends ContentStore<FileDocument, String> {
}
