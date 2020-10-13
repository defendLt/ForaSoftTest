package com.platdmit.forasofttest.domain.converters

interface TrackConverter<DbModel, ApiModel, DomainModal> {
    fun fromApiToDb(apiModel: ApiModel): DbModel
    fun fromDbToDomain(dbModel: DbModel): DomainModal
}