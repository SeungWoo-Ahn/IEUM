package com.ieum.data.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.ieum.data.TokenEntity
import com.ieum.data.mapper.toDomain
import com.ieum.data.mapper.toEntity
import com.ieum.domain.model.auth.Token
import java.io.InputStream
import java.io.OutputStream

internal object TokenSerializer : Serializer<Token?> {
    override val defaultValue: Token? = null

    override suspend fun readFrom(input: InputStream): Token? {
        try {
            val tokenEntity = TokenEntity.parseFrom(input)
            return tokenEntity.toDomain()
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("proto 파일을 읽을 수 없음", e)
        }
    }

    override suspend fun writeTo(t: Token?, output: OutputStream) {
        val tokenEntity = t?.toEntity() ?: TokenEntity.getDefaultInstance()
        tokenEntity.writeTo(output)
    }
}
