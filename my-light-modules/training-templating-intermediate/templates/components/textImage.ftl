[#-- Including our Freemarker Asset-Linking helper functions --]
[#include "/training-templating-intermediate/includes/functions/jcrImageLinkToAssetLink.ftl" ]

[#-- Possibility 1: Getting the asset (from the function 'jcrImageLinkToAsset') and from the asset the link --]
[#assign asset = jcrImageLinkToAsset(content.imageLink)!]
[#if asset?has_content]
    <img class="img-responsive" src="${asset.link}" alt="">
[/#if]
 
[#-- Possibility 2: Getting directly the asset's link from the function 'jcrImageLinkToAssetLink' --]
[#assign assetLink = jcrImageLinkToAssetLink(content.imageLink)!]
[#if assetLink?has_content]
    <img class="img-responsive" src="${assetLink}" alt="">
[/#if]