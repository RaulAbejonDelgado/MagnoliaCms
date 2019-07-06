[#-- Function: jcrImageLinkToAssetLink --]
[#function jcrImageLinkToAssetLink imageLinkValue="" renditionName=""] [#-- Test also once 'renditionName="original"' as init value --]
 
    [#assign asset = jcrImageLinkToAsset(imageLinkValue)!]
 
    [#if asset?has_content]
        [#if renditionName?has_content]
            [#assign assetLink = damfn.getAssetLink(asset, renditionName)!]
        [#else]
            [#assign assetLink = asset.link!]
        [/#if]
    [/#if]
     
    [#if assetLink?has_content]
        [#return assetLink]
    [#else]
        [#return null]
    [/#if]
     
[/#function]
 
 
[#-- Function: jcrImageLinkToAsset --]
[#function jcrImageLinkToAsset imageLinkValue=""]
 
    [#if imageLinkValue?has_content]
        [#assign asset = damfn.getAsset(imageLinkValue)!]
    [/#if]
     
    [#if asset?has_content]
        [#return asset]
    [#else]
        [#return null]
    [/#if]
     
[/#function]