<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" encoding="UTF-8"/>
    <!--http://localhost:8780/GeekWebSite/content/img/Geekf.png-->
    <xsl:template match="/">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="x" page-height="8.5in" page-width="11in"
                                       margin-top="0.5in" margin-bottom="0.5in" margin-left="1in" margin-right="1in">
                    <fo:region-body margin-top="0.5in" />
                    <fo:region-before extent="1in" />
                    <fo:region-after extent="0.1in" />
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="x">
                <fo:static-content flow-name="xsl-region-before">                                        
                    <fo:block-container position="absolute">
                        <fo:block>
                            <fo:external-graphic src="url(http://localhost:8780/GeekWebSite/content/img/Geekf.png)" content-height="10mm" />
                        </fo:block>
                    </fo:block-container>
                    <fo:block-container position="absolute">
                        <fo:block text-align="right" font-size="12pt" font-weight="bold">
                            Trang thông tin game - GeeK
                        </fo:block>
                        <fo:block text-align="right" font-size="10pt" font-family="Arial">
                            Phạm Hoàng Long - ĐH FPT
                        </fo:block>
                    </fo:block-container>
                </fo:static-content>
<!--                <fo:static-content flow-name="xsl-region-after">
                    <fo:block font-size="18pt" font-family="Arial" line-height="24pt" space-after.optimum="15pt" 
                              text-align="center" padding-top="3pt">
                    </fo:block>
                </fo:static-content>-->
                <fo:static-content flow-name="xsl-region-after">                                        
                    <fo:block text-align="center">
                        Trang <fo:page-number/>
                    </fo:block>
                </fo:static-content>
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-size="18pt" font-weight="bold" font-family="Arial" line-height="24pt"
                              space-after.optimum="10pt" text-align="center" padding-top="3pt">
                        Bảng xếp hạng game
                    </fo:block>
                    <fo:block-container>
                        <fo:table table-layout="fixed" border-color="#888888" border-width="medium" border-style="solid">
                            <fo:table-column column-width="30mm"/>
                            <fo:table-column column-width="100mm"/>
                            <fo:table-column column-width="50mm"/>
                            <fo:table-column column-width="50mm"/>
                            <fo:table-header background-color="#DFDFDF">
                                <fo:table-cell padding="2mm">
                                    <fo:block font-family="Arial" > HẠNG </fo:block>
                                </fo:table-cell>
                                <fo:table-cell padding="2mm">
                                    <fo:block font-family="Arial"> TÊN </fo:block>
                                </fo:table-cell>
                                <fo:table-cell padding="2mm">
                                    <fo:block font-family="Arial"> NỀN TẢNG </fo:block>
                                </fo:table-cell>
                                <fo:table-cell padding="2mm">
                                    <fo:block font-family="Arial"> ĐÁNH GIÁ </fo:block>
                                </fo:table-cell>  	   
                            </fo:table-header>
                            <fo:table-body>   
                                <xsl:for-each select="//*[local-name()='game']">
                                    <fo:table-row >
                                        <fo:table-cell padding="2mm" >
                                            <fo:block>
                                                <xsl:value-of select="./*[local-name()='id']" />
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell padding="2mm" >
                                            <fo:block>  
                                                <xsl:value-of select="./*[local-name()='name']" />
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell padding="2mm" >
                                            <fo:block> 
                                                <xsl:value-of select="./*[local-name()='platform']" />
                                            </fo:block>
                                        </fo:table-cell> 
                                        <fo:table-cell padding="2mm" >
                                            <fo:block> 
                                                <xsl:value-of select="./*[local-name()='overallScore']" />
                                            </fo:block>
                                        </fo:table-cell>        
                                    </fo:table-row>	
                                </xsl:for-each>		
                            </fo:table-body>
                        </fo:table>      
                    </fo:block-container>
                    
                    <xsl:for-each select="//*[local-name()='game']">
                        <fo:block-container height="8in">
                            <fo:block >
                                <fo:block-container font-size="2em" font-weight="bold" border-color="#009F00" color="#009F00" border-after-style="solid" border-width="0.5mm">
                                    <fo:block font-family="Arial"> Hạng <xsl:value-of select="./*[local-name()='id']"/></fo:block>
                                </fo:block-container>
                            </fo:block>
                            <fo:block>
                                <fo:table table-layout="fixed" >
                                    <fo:table-column column-width="35mm"/>
                                    <fo:table-column column-width="200mm"/>
                                    <fo:table-body>
                                        <fo:table-row>
                                            <fo:table-cell padding="2mm" >
                                                <fo:block>                      
                                                    <fo:external-graphic content-height="30mm"  content-width="30mm">
                                                        <xsl:attribute name="src">
                                                            <xsl:value-of select="./*[local-name()='thumbnail']"/>
                                                        </xsl:attribute>
                                                    </fo:external-graphic>                                         
                                                </fo:block>
                                            </fo:table-cell>
                                            <fo:table-cell padding="2mm" >
                                                <fo:block>
                                                    <fo:inline font-family="Arial" font-size="15pt" font-weight="bold">
                                                        Tên game:
                                                    </fo:inline>
                                                    <fo:inline font-family="Arial" font-size="16pt" >
                                                        <xsl:value-of select="./*[local-name()='name']" />
                                                    </fo:inline>
                                                </fo:block>
                                                <fo:block>
                                                    <fo:inline font-family="Arial" font-size="14pt" font-weight="bold">
                                                        Nền tảng:
                                                    </fo:inline>
                                                    <fo:inline font-family="Arial" font-size="15pt" >
                                                        <xsl:value-of select="./*[local-name()='platform']" />
                                                    </fo:inline>
                                                </fo:block>
                                                <fo:block>
                                                    <fo:inline font-family="Arial" font-size="14pt" font-weight="bold">
                                                        Nhà phát hành:
                                                    </fo:inline>
                                                    <fo:inline font-family="Arial" font-size="15pt" >
                                                        <xsl:value-of select="./*[local-name()='pulisherAndReleaseDate']" />
                                                    </fo:inline>
                                                </fo:block>
                                                <fo:block>
                                                    <fo:inline font-family="Arial" font-size="14pt" font-weight="bold">
                                                        Điểm:
                                                    </fo:inline>
                                                    <fo:inline font-family="Arial" font-size="15pt" >
                                                        <xsl:value-of select="./*[local-name()='overallScore']" /> với <xsl:value-of select="./*[local-name()='totalVote']" />
                                                    </fo:inline>
                                                </fo:block>
                                            </fo:table-cell>
                                        </fo:table-row>			
                                    </fo:table-body>
                                </fo:table>  
                            </fo:block>
        
                            <fo:table table-layout="fixed" border-color="#888888" border-width="medium" border-style="solid">
                                <fo:table-column column-width="80mm"/>
                                <fo:table-column column-width="80mm"/>
                                <fo:table-column column-width="70mm"/>
                                <fo:table-header background-color="#DFDFDF">
                                    <fo:table-cell padding="2mm">
                                        <fo:block font-family="Arial" > NGƯỜI ĐÁNH GIÁ </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell padding="2mm">
                                        <fo:block font-family="Arial"> ĐIỂM </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell padding="2mm">
                                        <fo:block font-family="Arial"> NGÀY ĐÁNH GIÁ </fo:block>
                                    </fo:table-cell>  	   
                                </fo:table-header>
                                <fo:table-body>   
                                    <xsl:for-each select="./*[local-name()='gameRating']">
                                        <fo:table-row >
                                            <fo:table-cell padding="2mm" >
                                                <fo:block>
                                                    <xsl:value-of select="./*[local-name()='reviewer']" />
                                                </fo:block>
                                            </fo:table-cell>
                                            <fo:table-cell padding="2mm" >
                                                <fo:block>  
                                                    <xsl:value-of select="./*[local-name()='score']" />
                                                </fo:block>
                                            </fo:table-cell>
                                            <fo:table-cell padding="2mm" >
                                                <fo:block> 
                                                    <xsl:value-of select="./*[local-name()='reviewedDate']" />
                                                </fo:block>
                                            </fo:table-cell>        
                                        </fo:table-row>	
                                    </xsl:for-each>		
                                </fo:table-body>
                            </fo:table>  
                        </fo:block-container>
                    </xsl:for-each>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>

</xsl:stylesheet>
