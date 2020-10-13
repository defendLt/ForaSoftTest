package com.platdmit.forasofttest.domain.converters

interface AlbumConverter<DbModel, ApiModel, DomainModal> {
    fun fromApiToDb(apiModel: ApiModel): DbModel
    fun fromDbToDomain(dbModel: DbModel): DomainModal
}