package cesium.processor;

import cesium.factory.ResourcesProcessorFactoryImpl;
import cesium.holder.CSSPropertyHolderImpl;
import cesium.holder.ResourcesHolder;
import cesium.op.ExtJSRescaleOp;
import cesium.op.ForegroundShiftOp;
import org.apache.batik.css.parser.CSSLexicalUnit;
import org.springframework.context.ApplicationContext;
import org.w3c.css.sac.LexicalUnit;

import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImageOp;

/**
 * @project: Theme Builder for ExtJS 3.x
 * @Description:
 * @license: LGPL_v3
 * @author: Sergey Chentsov (extjs id: iv_ekker)
 * @mailto: sergchentsov@gmail.com
 * @version: 1.0.0
 * @Date: 17.08.2009
 * @Time: 17:00:51
 */
public class CSSPropertyProcessorImpl extends AbstractResourcesProcessor {
    public CSSPropertyProcessorImpl(ApplicationContext context, ResourcesProcessorFactoryImpl resourcesProcessorFactory) {
        this.setContext(context);
        this.setResourcesProcessorFactory(resourcesProcessorFactory);
    }

    public ResourcesHolder process(ResourcesHolder resHolder,
                                   ExtJSRescaleOp brightenOp,
                                   ForegroundShiftOp foregroundOp,
                                   ExtJSRescaleOp liteOp, ExtJSRescaleOp bgOp,
                                   ExtJSRescaleOp fontOp, ExtJSRescaleOp transparencyOp,
                                   ExtJSRescaleOp borderOp, AffineTransformOp affineTransformOp,
                                   ExtJSRescaleOp headerFontOp, BufferedImageOp shadowTransparencyOp,
                                   ExtJSRescaleOp headerOp, ResourcesHolder toolsetSchemaHolder, String toolsetName,
                                   String familyHeaderFont, String weightHeaderFont, byte sizeHeaderFontDiff, String familyFont, String weightFont, byte sizeFontDiff, ResourcesHolder drawableSchemaHolder) {
        ResourcesHolder newHolder = super.process(resHolder, brightenOp, foregroundOp,
                liteOp, bgOp, fontOp, transparencyOp, borderOp, (AffineTransformOp) affineTransformOp,
                headerFontOp, shadowTransparencyOp, headerOp, toolsetSchemaHolder, toolsetName,
                familyHeaderFont, weightHeaderFont, sizeHeaderFontDiff, familyFont, weightFont, sizeFontDiff, drawableSchemaHolder);
        if (resHolder instanceof CSSPropertyHolderImpl){
            CSSPropertyHolderImpl resCssPropHolder = (CSSPropertyHolderImpl)resHolder;
            CSSPropertyHolderImpl newCssPropHolder = (CSSPropertyHolderImpl)newHolder;
            newCssPropHolder.setImportant(resCssPropHolder.isImportant());
            LexicalUnit lexicalUnit = (LexicalUnit) resHolder.getContent();
            LexicalUnit newLexicalUnit = lexicalUnit;
            short lexicalUnitType = lexicalUnit.getLexicalUnitType();
            if (resCssPropHolder.isProcessingNotNeeded()){
                 // Do nothing processing not needed !!!
            } else if (lexicalUnitType == LexicalUnit.SAC_RGBCOLOR){
                int oldIntegerValueR = lexicalUnit.getParameters().getIntegerValue();
                int oldIntegerValueG = lexicalUnit.getParameters().getNextLexicalUnit()
                        .getNextLexicalUnit().getIntegerValue();
                int oldIntegerValueB = lexicalUnit.getParameters().getNextLexicalUnit()
                        .getNextLexicalUnit().getNextLexicalUnit().getNextLexicalUnit()
                        .getIntegerValue();

                byte[] bytesR = {(byte) oldIntegerValueR};
                byte[] bytesG = {(byte) oldIntegerValueG};
                byte[] bytesB = {(byte) oldIntegerValueB};

                if (null!=borderOp&&resCssPropHolder.isBorderColor()){
                    float[] scaleFactors = borderOp.getScaleFactors(null);
                    float[] offsets = borderOp.getOffsets(null);
                    if (offsets[0]!=0||offsets[1]!=0||offsets[2]!=0)
                        borderOp.rescale(bytesR, bytesG, bytesB,null, scaleFactors, offsets,null,null);
                } else if (null!=headerFontOp&&resCssPropHolder.isHeaderFontColor()){
                    float[] scaleFactors = headerFontOp.getScaleFactors(null);
                    float[] offsets = headerFontOp.getOffsets(null);
                    if (offsets[0]!=0||offsets[1]!=0||offsets[2]!=0)
                        headerFontOp.rescale(bytesR, bytesG, bytesB,null, scaleFactors, offsets,null,null);
                } else if (null!=fontOp&&resCssPropHolder.isFontColor()){
                    float[] scaleFactors = fontOp.getScaleFactors(null);
                    float[] offsets = fontOp.getOffsets(null);
                    if (offsets[0]!=0||offsets[1]!=0||offsets[2]!=0)
                        fontOp.rescale(bytesR, bytesG, bytesB,null, scaleFactors, offsets,null,null);
                } else if (null!=bgOp&&resCssPropHolder.isBackground()){
                    float[] scaleFactors = bgOp.getScaleFactors(null);
                    float[] offsets = bgOp.getOffsets(null);
                    if (offsets[0]!=0||offsets[1]!=0||offsets[2]!=0)
                        bgOp.rescale(bytesR, bytesG, bytesB,null, scaleFactors, offsets,null,null);
                }else if (null!=foregroundOp&&resCssPropHolder.isForeground()){
                    foregroundOp.rescale(bytesR, bytesG, bytesB);
                }else if (null!=brightenOp){
                    float[] scaleFactors = brightenOp.getScaleFactors(null);
                    float[] offsets = brightenOp.getOffsets(null);
                    if (offsets[0]!=0||offsets[1]!=0||offsets[2]!=0)
                        brightenOp.rescale(bytesR, bytesG, bytesB,null, scaleFactors, offsets,null,null);
                }

                int newIntegerValueR =( bytesR[0]&0xff);
                int newIntegerValueG = (bytesG[0]&0xff);
                int newIntegerValueB = (bytesB[0]&0xff);

                CSSLexicalUnit cssLexicalUnitR = CSSLexicalUnit.createInteger(newIntegerValueR, null);
                CSSLexicalUnit cssSimpleLexicalUnitAfterR = CSSLexicalUnit.createSimple(
                        LexicalUnit.SAC_OPERATOR_COMMA ,cssLexicalUnitR);
                cssLexicalUnitR.setNextLexicalUnit(cssSimpleLexicalUnitAfterR);

                CSSLexicalUnit cssLexicalUnitG = CSSLexicalUnit.createInteger(
                        newIntegerValueG, cssSimpleLexicalUnitAfterR);
                CSSLexicalUnit cssSimpleLexicalUnitAfterG = CSSLexicalUnit.createSimple(
                        LexicalUnit.SAC_OPERATOR_COMMA ,cssLexicalUnitG);
                cssLexicalUnitG.setNextLexicalUnit(cssSimpleLexicalUnitAfterG);

                CSSLexicalUnit cssLexicalUnitB = CSSLexicalUnit.createInteger(
                        newIntegerValueB, cssSimpleLexicalUnitAfterG);
                cssSimpleLexicalUnitAfterG.setNextLexicalUnit(cssLexicalUnitB);


                newLexicalUnit = CSSLexicalUnit.createPredefinedFunction(
                        lexicalUnit.getLexicalUnitType()
                        , cssLexicalUnitR
                        , null);

            } else

            if ((lexicalUnitType == LexicalUnit.SAC_IDENT)||(lexicalUnitType == LexicalUnit.SAC_STRING_VALUE)){
                if (null!=familyHeaderFont&&resCssPropHolder.isHeaderFontFamily()){
                    newLexicalUnit = CSSLexicalUnit.createString(lexicalUnitType, familyHeaderFont, null );
                } else if (null!=weightHeaderFont&&resCssPropHolder.isHeaderFontWeight()){
                    newLexicalUnit = CSSLexicalUnit.createString(lexicalUnitType, weightHeaderFont, null );
                } else
                if (null!=familyFont&&resCssPropHolder.isFontFamily()){
                    newLexicalUnit = CSSLexicalUnit.createString(lexicalUnitType, familyFont, null );
                } else if (null!=weightFont&&resCssPropHolder.isFontWeight()){
                    newLexicalUnit = CSSLexicalUnit.createString(lexicalUnitType, weightFont, null );
                } else if (resCssPropHolder.isFormTriggerBorderStyle()&&"tp".equals(toolsetName)){
                    newLexicalUnit = CSSLexicalUnit.createString(LexicalUnit.SAC_STRING_VALUE, "none none none solid",null);
                }
            } else
            if ((lexicalUnitType == LexicalUnit.SAC_PIXEL)){
                float oldvalue = lexicalUnit.getFloatValue();
                if (resCssPropHolder.isHeaderFontSize()&&0!=sizeHeaderFontDiff){
                    newLexicalUnit = CSSLexicalUnit.createFloat(lexicalUnitType, oldvalue+sizeHeaderFontDiff, null );
                } else
                if (resCssPropHolder.isFontSize()&&0!=sizeFontDiff){
                    newLexicalUnit = CSSLexicalUnit.createFloat(lexicalUnitType, oldvalue+sizeFontDiff, null );
                }
            } else if ((lexicalUnitType == LexicalUnit.SAC_INTEGER)||(lexicalUnitType == LexicalUnit.SAC_REAL)){
                if (resCssPropHolder.isResizableOpacityCSSProperty()&&null!=transparencyOp){
                    int oldOpacity = lexicalUnit.getIntegerValue()*255;
                    float newOpacity = (oldOpacity + transparencyOp.getOffsets(null)[3])/255;
                    newLexicalUnit = CSSLexicalUnit.createFloat(lexicalUnitType,newOpacity , null );
                }
            } else
            if (lexicalUnitType == LexicalUnit.SAC_URI){
                String resourcesPath = (String) getContext().getBean("resourcesPath");

/*            String path2resource = resourcesPath + "/" + lexicalUnit.getStringValue();
            Set fileKeys = resHolder.keySet();
            for (Iterator iterator = fileKeys.iterator(); iterator.hasNext();) {
                String filePath = (String) iterator.next();
                byte[] data =(byte[]) resHolder.get(filePath);
                //todo: sc processing image data

                //end processing
                newHolder.put(filePath, data);
            }*/

                /*ResourcesLoader resourcesLoader = loaderFactory.getResourcesLoader(path2resource, context);
            ResourcesHolder resourcesHolder = resourcesLoader.loadResources(path2resource, context);*/
                String resUrl = lexicalUnit.getStringValue();
                newLexicalUnit = CSSLexicalUnit.createString(LexicalUnit.SAC_URI, resUrl,null );
/*                System.out.println(
                        new StringBuilder().append("path2resource = ")
                                .append(resourcesPath)
                                .append(File.separator)
                                .append(resUrl).toString());*/
            }
/*            if (lexicalUnitType == LexicalUnit.SAC_IDENT
                *//*||lexicalUnitType == LexicalUnit.SAC_STRING_VALUE*//*){
                //SystemColorSupport.getSystemColor(lexicalUnit.toString()); //CSSConstants
                //newLexicalUnit = CSSLexicalUnit.createString(lexicalUnitType, lexicalUnit.getStringValue(),null );
                //propValue = lxUnit.getStringValue()+";";
            }*/
/*        if (lexicalUnitType == LexicalUnit.SAC_INTEGER){
            propValue = lxUnit.getIntegerValue()+";";
        }
        if (lexicalUnitType == LexicalUnit.SAC_PIXEL
                ||lexicalUnitType == LexicalUnit.SAC_REAL){
            propValue = lxUnit.getFloatValue()+";";
        }*/

            newHolder.setContent(newLexicalUnit);
        }

        return newHolder;
    }
}
